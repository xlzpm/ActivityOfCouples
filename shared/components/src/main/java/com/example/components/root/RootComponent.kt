package com.example.components.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.components.app.AppComponent
import com.example.components.auth.AuthComponent
import kotlinx.serialization.Serializable

interface RootComponent {
    val child: Value<ChildStack<*, Child>>

    @Serializable
    sealed class Child{
        @Serializable
        class Auth(val authComponent: AuthComponent): Child()
        @Serializable
        class App(val appComponent: AppComponent): Child()
    }
}