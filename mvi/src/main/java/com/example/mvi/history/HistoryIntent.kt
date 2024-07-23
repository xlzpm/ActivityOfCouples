package com.example.mvi.history

sealed class HistoryIntent {
    data class LoadHistory(val pairId: String): HistoryIntent()
}