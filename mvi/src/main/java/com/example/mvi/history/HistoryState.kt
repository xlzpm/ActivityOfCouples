package com.example.mvi.history

import kotlinx.serialization.Serializable

@Serializable
data class HistoryState(
    val activities: List<Pair<String, String>> = emptyList(),
    val error: String? = null
)
