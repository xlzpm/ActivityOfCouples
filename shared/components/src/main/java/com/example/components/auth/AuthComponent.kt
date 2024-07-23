package com.example.components.auth

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.components.auth.signIn.SignInComponent
import com.example.components.auth.signUp.SignUpComponent
import kotlinx.serialization.Serializable

interface AuthComponent {
    val stack: Value<ChildStack<*, Child>>

    @Serializable
    sealed class Child{
        class SignIn(val signInComponent: SignInComponent): Child()
        class SignUp(val signUpComponent: SignUpComponent): Child()
    }
}