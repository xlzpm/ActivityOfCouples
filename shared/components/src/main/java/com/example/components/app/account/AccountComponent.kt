package com.example.components.app.account

import com.arkivanov.decompose.value.Value
import com.example.mvi.account.AccountIntent
import com.example.mvi.account.AccountState

interface AccountComponent {
    val state: Value<AccountState>

    fun processIntent(accountIntent: AccountIntent)
}