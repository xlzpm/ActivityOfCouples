package com.example.app.ui.app.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.app.ui.app.main.components.CubeLoadingAnimation
import com.example.app.ui.app.main.components.DialogWithGetActivity
import com.example.app.ui.app.main.components.DialogWithSendActivity
import com.example.components.app.main.MainComponent
import com.example.mvi.main.MainIntent

@Composable
fun Main(
    mainComponent: MainComponent,
) {
    val state by mainComponent.state.subscribeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (state.isLoading) {
            CubeLoadingAnimation()
        } else if (state.showActivityDialog == true) {
            DialogWithGetActivity(
                activity = state.activityFromPartner ?: state.error.orEmpty(),
                onAccept = {
                    mainComponent.processIntent(
                        MainIntent.AcceptActivity
                    )
                    mainComponent.processIntent(MainIntent.ShowPartnerActivity)
                },
                onDismiss = {
                    mainComponent.processIntent(MainIntent.ShowPartnerActivity)
                }
            )
        } else if (state.showPartnerActivityDialog == true) {
            DialogWithSendActivity(
                partnerActivity = state.activityDropped ?: state.error.orEmpty(),
                onDismiss = { }
            )
        }

        Button(onClick = {
            mainComponent.processIntent(MainIntent.GenerationAndSendActivity)
        }) {
            Text("Generate Activity")
        }
    }
    state.error?.let { Text(text = it) }
}
