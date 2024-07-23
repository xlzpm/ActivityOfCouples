package com.example.firestore.data.repository

import com.example.firestore.domain.repository.HistoryRepo
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class HistoryRepoImpl(
    private val firestore: FirebaseFirestore
) : HistoryRepo {
    override suspend fun getHistory(pairId: String): List<Pair<String, String>> =
        suspendCoroutine { continuation ->
            firestore.collection("pairs")
                .document(pairId)
                .collection("activities")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val activities = querySnapshot.documents.map { documentSnapshot ->
                        val date = documentSnapshot.getString("date") ?: ""
                        val activity = documentSnapshot.getString("activity") ?: ""
                        Pair(activity, date)
                    }
                    continuation.resume(activities)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
}