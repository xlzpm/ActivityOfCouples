package com.example.firestore.domain.repository

interface HistoryRepo {
    suspend fun getHistory(pairId: String): List<Pair<String, String>>
}