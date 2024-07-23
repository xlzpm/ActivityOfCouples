package com.example.mvi.signIn

import kotlinx.serialization.Serializable

@Serializable
data class SignInState(
    val email: String = "",
    val password: String = "",
    val error: String? = null
)
