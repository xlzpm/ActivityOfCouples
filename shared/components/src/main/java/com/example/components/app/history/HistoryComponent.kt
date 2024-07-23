package com.example.components.app.history

import com.arkivanov.decompose.value.Value
import com.example.mvi.history.HistoryIntent
import com.example.mvi.history.HistoryState

interface HistoryComponent {
    val state: Value<HistoryState>

    fun processIntent(intent: HistoryIntent)
}