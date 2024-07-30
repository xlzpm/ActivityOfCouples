package com.example.mvi.main

import kotlinx.serialization.Serializable

@Serializable
data class MainState(
    val pairId: String? = null,
    val userId: String? = null,
    val activityFromPartner: String? = null,
    val activityDropped: String? = null,
    val showActivityDialog: Boolean? = null,
    val showPartnerActivityDialog: Boolean? = null,
    val activityAccepted: Boolean? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
