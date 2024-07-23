package com.example.mvi.account

import kotlinx.serialization.Serializable

@Serializable
data class AccountState(
    val imageYour: String? = null,
    val imagePartner: String? = null,
    val myName: String? = "",
    val namePartner: String = "",
    val titleButton: String = "Partner search",
    val isSearching: Boolean = false,
    val error: String? = null
)