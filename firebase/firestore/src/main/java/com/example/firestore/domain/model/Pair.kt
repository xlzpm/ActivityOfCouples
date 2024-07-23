package com.example.firestore.domain.model

data class PairOfUser(
    val pairId: String = "",
    val userFirst: String = "",
    val userSecond: String = "",
    val activityFirst: String? = null,
    val activitySecond: String? = null
)
