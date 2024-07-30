package com.example.mvi.history

sealed class HistoryIntent {
    data object LoadHistory : HistoryIntent()
}