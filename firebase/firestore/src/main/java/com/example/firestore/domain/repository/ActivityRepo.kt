package com.example.firestore.domain.repository

interface ActivityRepo {
    suspend fun sendActivityToPartner(pairId: String, userId: String, activity: String)
    suspend fun acceptActivity(pairId: String, userId: String)
    suspend fun getCurrentActivity(pairId: String, userId: String): String?
    suspend fun checkBothAccepted(pairId: String): Boolean
}