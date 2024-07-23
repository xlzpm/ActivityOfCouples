package com.example.auth.domain.repository.signUp

import com.example.auth.domain.model.User

interface SignUpRepository {
    suspend fun signUp(email: String, password: String): User
}