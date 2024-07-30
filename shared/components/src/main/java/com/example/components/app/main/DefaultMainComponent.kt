package com.example.components.app.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.example.firestore.domain.repository.ActivityRepo
import com.example.mvi.main.MainIntent
import com.example.mvi.main.MainState
import com.example.network.domain.model.RandomActivity
import com.example.network.domain.repository.RandomActivityRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DefaultMainComponent(
    private val componentContext: ComponentContext,
    private val activityRepo: ActivityRepo,
    private val randomActivityRepository: RandomActivityRepository,
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
): MainComponent, ComponentContext by componentContext {
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private var _state = MutableValue(
        stateKeeper.consume(MAIN_COMPONENT, MainState.serializer()) ?: MainState()
    )
    override val state: Value<MainState> = _state

    init {
        coroutineScope.launch {
            try {
                val userId = getCurrentUserId()
                val pairId = getPairId(userId)
                _state.update { it.copy(userId = userId, pairId = pairId) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    private fun getCurrentUserId(): String {
        return firebaseAuth.currentUser?.uid ?: throw Exception("User not authenticated")
    }

    private suspend fun getPairId(userId: String): String {
        val db = firestore
        val pairsCollection = db.collection("pairs")

        val query = pairsCollection
            .whereEqualTo("userFirstId", userId)
            .get()
            .await()

        val pairId = query.documents.firstOrNull()?.id
        return pairId ?: throw Exception("Pair not found for userId: $userId")
    }

    override fun processIntent(mainIntent: MainIntent) {
        when(mainIntent){
            is MainIntent.GenerationAndSendActivity -> generationAndSendActivity(
                pairId = _state.value.pairId.orEmpty(), userId = _state.value.userId.orEmpty()
            )
            is MainIntent.AcceptActivity -> acceptActivity(
                pairId = _state.value.pairId.orEmpty(), userId = _state.value.userId.orEmpty()
            )
            is MainIntent.ShowPartnerActivity -> showPartnerActivity(
                pairId = _state.value.pairId.orEmpty(), userId = _state.value.userId.orEmpty()
            )
        }
    }

    private fun generationAndSendActivity(pairId: String, userId: String){
        coroutineScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val activity = randomActivityRepository.getRandomActivityRepository().activity
                activityRepo.sendActivityToPartner(pairId = pairId,
                    userId = userId, activity = activity)
                _state.update { it.copy(activityDropped = activity,
                    isLoading = false, showActivityDialog = true) }
            } catch (e: Exception){
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    private fun acceptActivity(pairId: String, userId: String){
        coroutineScope.launch {
            try{
                activityRepo.acceptActivity(pairId = pairId, userId = userId)
                _state.update {
                    it.copy( activityAccepted = true, showActivityDialog = false,
                        showPartnerActivityDialog = true)
                }
            } catch (e: Exception){
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    private fun showPartnerActivity(pairId: String, userId: String){
        coroutineScope.launch {
            try {
                val partnerActivity = activityRepo.getCurrentActivity(pairId = pairId, userId = userId)
                _state.update { it.copy(activityFromPartner = partnerActivity,
                    showPartnerActivityDialog = true )
                }
            } catch (e: Exception){
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    companion object{
        const val MAIN_COMPONENT = "MAIN_COMPONENT"
    }
}