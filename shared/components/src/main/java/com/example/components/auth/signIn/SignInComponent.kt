package com.example.components.auth.signIn

import com.arkivanov.decompose.value.Value
import com.example.mvi.signIn.SignInIntent
import com.example.mvi.signIn.SignInState

interface SignInComponent {
    val state: Value<SignInState>

    fun processIntent(intent: SignInIntent)
    fun onBackClicked()
}