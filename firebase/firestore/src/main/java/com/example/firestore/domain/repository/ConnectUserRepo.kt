package com.example.firestore.domain.repository

interface ConnectUserRepo {
    suspend fun createPair(userFirst: String, userSecond: String): String
}