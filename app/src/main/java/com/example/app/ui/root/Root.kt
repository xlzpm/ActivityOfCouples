package com.example.app.ui.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.example.app.ui.app.App
import com.example.app.ui.auth.Auth
import com.example.components.root.RootComponent
import com.example.components.root.RootComponent.Child

@Composable
fun Root(
    rootComponent: RootComponent,
){
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Children(
            stack = rootComponent.child,
            modifier = Modifier.fillMaxSize(),
            animation = stackAnimation( fade() + scale())
        ) {
            when( val instance = it.instance){
                is Child.Auth -> Auth(authComponent = instance.authComponent)
                is Child.App -> App(
                    appComponent = instance.appComponent,
                )
            }
        }
    }
}