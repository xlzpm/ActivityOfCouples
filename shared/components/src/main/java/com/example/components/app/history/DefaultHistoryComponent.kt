package com.example.components.app.history

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.example.firestore.domain.repository.HistoryRepo
import com.example.mvi.history.HistoryIntent
import com.example.mvi.history.HistoryState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DefaultHistoryComponent(
    private val componentContext: ComponentContext,
    private val historyRepo: HistoryRepo
): HistoryComponent, ComponentContext by componentContext {
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _state = MutableValue(
        stateKeeper.consume(HISTORY_COMPONENT, HistoryState.serializer()) ?: HistoryState()
    )
    override val state: Value<HistoryState> = _state

    override fun processIntent(intent: HistoryIntent) =
        when (intent){
            is HistoryIntent.LoadHistory -> loadHistory(intent.pairId)
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