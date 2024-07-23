package com.example.mvi.signIn

sealed class SignInIntent {
    data class EmailChanged(val email: String): SignInIntent()
    data class PasswordChanged(val password: String): SignInIntent()
    data object SignIn: SignInIntent()
    data object SignUpScreen: SignInIntent()
}