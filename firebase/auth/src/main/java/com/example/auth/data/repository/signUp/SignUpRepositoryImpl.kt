package com.example.auth.data.repository.signUp

import com.example.auth.domain.model.User
import com.example.auth.domain.repository.signUp.SignUpRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class SignUpRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : SignUpRepository {
    override suspend fun signUp(email: String, password: String): User =
        suspendCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val user = result.user
                    if (user != null){
                        continuation.resume(value = User(email = user.email.orEmpty()))
                    }
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

}