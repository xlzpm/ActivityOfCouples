package com.example.components.app.main

import com.arkivanov.decompose.value.Value
import com.example.mvi.main.MainIntent
import com.example.mvi.main.MainState

interface MainComponent {
    val state: Value<MainState>

    fun processIntent(mainIntent: MainIntent)
}