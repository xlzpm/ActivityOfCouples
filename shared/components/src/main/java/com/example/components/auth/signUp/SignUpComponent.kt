package com.example.components.auth.signUp

import com.arkivanov.decompose.value.Value
import com.example.mvi.signUp.SignUpIntent
import com.example.mvi.signUp.SignUpState


interface SignUpComponent {
    val state: Value<SignUpState>

    fun processIntent(intent: SignUpIntent)
    fun onPrevClicked()
}