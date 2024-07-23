package com.example.network.domain.model

import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Serializable
data class RandomActivity(
    val key: String,
    val activity: String
)

