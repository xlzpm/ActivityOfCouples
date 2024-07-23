package com.example.firestore.data.repository

import com.example.firestore.domain.repository.ConnectUserRepo
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ConnectUserRepoImpl(
    private val firestore: FirebaseFirestore
): ConnectUserRepo {
    override suspend fun createPair(userFirst: String, userSecond: String): String {
        val newPair = hashMapOf(
            "userFirstId" to userFirst,
            "userSecondId" to userSecond,
            "currentActivity" to hashMapOf(
                "userFirst" to hashMapOf(
                    "activity" to "",
                    "accepted" to false
                ),
                "userSecond" to hashMapOf(
                    "activity" to "",
                    "accepted" to false
                )
            ),
            "timestamp" to FieldValue.serverTimestamp()
        )

        val pairDocRef = firestore.collection("pairs").add(newPair).await()
        firestore.collection("users")
            .document(userFirst).update("partnerId", userSecond).await()
        firestore.collection("users")
            .document(userSecond).update("partnerId", userFirst).await()

        return pairDocRef.id
    }
}