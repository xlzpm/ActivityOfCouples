package com.example.auth.domain.repository.signIn

import com.example.auth.domain.model.User

interface SignInRepository {
    suspend fun signIn(email: String, password: String): User
}