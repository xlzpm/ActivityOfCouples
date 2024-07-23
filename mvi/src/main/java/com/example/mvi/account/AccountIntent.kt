package com.example.mvi.account


sealed class AccountIntent {
    data class ChangedNamePartner(val name: String): AccountIntent()
    data object InvitePartner: AccountIntent()
    data class ChangedImage(val image: String): AccountIntent()
}