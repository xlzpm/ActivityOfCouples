package com.example.firestore.data.repository

import com.example.firestore.domain.repository.ActivityRepo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ActivityRepoImpl(
    private val firestore: FirebaseFirestore
) : ActivityRepo {
    override suspend fun sendActivityToPartner(pairId: String, userId: String, activity: String) {
        val pairDocRef = firestore.collection("pairs").document(pairId)
        val userKey = if (pairDocRef.get().await().getString("userFirstId") == userId)
            "userFirst" else "userSecond"
        pairDocRef.update("currentActivity.$userKey.activity", activity,
            "currentActivity.$userKey.accepted")
    }

    override suspend fun acceptActivity(pairId: String, userId: String) {
        val pairDocRef = firestore.collection("pairs").document(pairId)
        val userKey = if (pairDocRef.get().await().getString("userFirstId") == userId)
            "userFirst" else "userSecond"
        pairDocRef.update("currentActivity.$userKey.accepted", true).await()
    }

    override suspend fun getCurrentActivity(pairId: String, userId: String): String? {
        val pairDocRef = firestore.collection("pairs").document(pairId).get().await()
        val userKey = if (pairDocRef.getString("userFirstId") == userId)
            "userFirst" else "userSecond"
        return pairDocRef.getString("currentActivity.$userKey.activity")
    }

    override suspend fun checkBothAccepted(pairId: String): Boolean {
        val pairDocRef = firestore.collection("pairs").document(pairId).get().await()
        val userFirstAccepted = pairDocRef
            .getBoolean("currentActivity.userFirst.accepted") ?: false
        val userSecondAccepted = pairDocRef
            .getBoolean("currentActivity.userSecond.accepted") ?: false
        return userFirstAccepted && userSecondAccepted
    }
}