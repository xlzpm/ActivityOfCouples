package com.example.components.app.history

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.example.firestore.domain.repository.HistoryRepo
import com.example.mvi.history.HistoryIntent
import com.example.mvi.history.HistoryState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DefaultHistoryComponent(
    private val componentContext: ComponentContext,
    private val historyRepo: HistoryRepo,
    private val firebaseAuth: FirebaseAuth
): HistoryComponent, ComponentContext by componentContext {
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _state = MutableValue(
        stateKeeper.consume(HISTORY_COMPONENT, HistoryState.serializer()) ?: HistoryState()
    )
    override val state: Value<HistoryState> = _state

    init{
        coroutineScope.launch {
            try {
                val userId = getCurrentUserId()
                _state.update { it.copy(userId = userId) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    private fun getCurrentUserId(): String {
        return firebaseAuth.currentUser?.uid ?: throw Exception("User not authenticated")
    }

    override fun processIntent(intent: HistoryIntent) =
        when (intent){
            is HistoryIntent.LoadHistory -> loadHistory(_state.value.userId.orEmpty())
        }

    private fun loadHistory(pairId: String){
        coroutineScope.launch {
            try{
                val activities = historyRepo.getHistory(pairId)
                _state.update { it.copy(activities = activities) }
            } catch (e: Exception){
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    companion object{
        const val HISTORY_COMPONENT = "HISTORY_COMPONENT"
    }
}