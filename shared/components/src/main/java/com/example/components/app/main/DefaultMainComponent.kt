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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DefaultMainComponent(
    private val componentContext: ComponentContext,
    private val activityRepo: ActivityRepo,
    private val randomActivityRepository: RandomActivityRepository
): MainComponent, ComponentContext by componentContext {
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private var _state = MutableValue(
        stateKeeper.consume(MAIN_COMPONENT, MainState.serializer()) ?: MainState()
    )
    override val state: Value<MainState> = _state

    override fun processIntent(mainIntent: MainIntent) {
        when(mainIntent){
            is MainIntent.GenerationAndSendActivity -> generationAndSendActivity(
                pairId = mainIntent.pairId, userId = mainIntent.userId
            )
            is MainIntent.AcceptActivity -> acceptActivity(
                pairId = mainIntent.pairId, userId = mainIntent.userId
            )
            is MainIntent.ShowPartnerActivity -> showPartnerActivity(
                pairId = mainIntent.pairId, userId = mainIntent.userId
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