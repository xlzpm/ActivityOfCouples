package com.example.auth.data.repository.signIn

import com.example.auth.domain.model.User
import com.example.auth.domain.repository.signIn.SignInRepository
import com.google.firebase.auth.FirebaseAuth
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class SignInRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : SignInRepository {
    override suspend fun signIn(email: String, password: String): com.example.auth.domain.model.User =
        suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val user = result.user
                    if (user != null){
                        continuation.resume(value = com.example.auth.domain.model.User(email = user.email.orEmpty()))
                    }
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
}