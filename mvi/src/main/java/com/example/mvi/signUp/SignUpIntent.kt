package com.example.mvi.signUp

import com.example.mvi.signIn.SignInIntent


sealed class SignUpIntent {
    data class EmailChanged(val email: String): SignUpIntent()
    data class PasswordChanged(val password: String): SignUpIntent()
    data class IsPasswordVisibleChanged(val isPasswordVisibleChanged: Boolean): SignUpIntent()
    data object SignUp: SignUpIntent()
    data object SignInScreen: SignUpIntent()
}