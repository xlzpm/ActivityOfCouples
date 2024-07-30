package com.example.components.app

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.components.app.account.AccountComponent
import com.example.components.app.history.HistoryComponent
import com.example.components.app.main.MainComponent
import kotlinx.serialization.Serializable

interface AppComponent {
    val child: Value<ChildStack<*,Child>>

    fun onMainClicked()
    fun onHistoryClicked()
    fun onAccountClicked()

    @Serializable
    sealed class Child{
        @Serializable
        class Main(val mainComponent: MainComponent): Child()
        @Serializable
        class History(val historyComponent: HistoryComponent): Child()
        @Serializable
        class Account(val accountComponent: AccountComponent): Child()
    }
}