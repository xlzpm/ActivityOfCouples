package com.example.mvi.signUp

import kotlinx.serialization.Serializable

@Serializable
data class SignUpState(
    val email: String = "",
    val password: String = "",
    val error: String? = null
)
