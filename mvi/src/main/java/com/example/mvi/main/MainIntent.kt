package com.example.mvi.main

sealed class MainIntent {
    data class GenerationAndSendActivity(val pairId: String, val userId: String): MainIntent()
    data class AcceptActivity(val pairId: String, val userId: String): MainIntent()
    data class ShowPartnerActivity(val pairId: String, val userId: String): MainIntent()
}