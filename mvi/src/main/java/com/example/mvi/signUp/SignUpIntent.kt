package com.example.mvi.signUp


sealed class SignUpIntent {
    data class EmailChanged(val email: String): SignUpIntent()
    data class PasswordChanged(val password: String): SignUpIntent()
    data object SignUp: SignUpIntent()
    data object SignInScreen: SignUpIntent()
}