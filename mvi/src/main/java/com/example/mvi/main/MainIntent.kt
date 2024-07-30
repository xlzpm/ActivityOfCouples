package com.example.mvi.main

sealed class MainIntent {
    data object GenerationAndSendActivity : MainIntent()
    data object AcceptActivity : MainIntent()
    data object ShowPartnerActivity : MainIntent()
}