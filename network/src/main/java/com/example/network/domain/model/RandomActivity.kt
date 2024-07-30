package com.example.network.domain.model

import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Serializable
data class RandomActivity(
    val activity: String,
    val type: String,
    val participants: Int,
    val price: Float,
    val link: String?,
    val key: String,
    val accessibility: Float
)

