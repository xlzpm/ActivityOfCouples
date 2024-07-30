package com.example.app.ui.auth

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.example.app.ui.auth.signIn.SignIn
import com.example.app.ui.auth.signUp.SignUp
import com.example.components.auth.AuthComponent
import com.example.components.auth.AuthComponent.Child

@Composable
fun Auth(
    authComponent: AuthComponent
){
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Children(
            stack = authComponent.stack,
            modifier = Modifier.fillMaxSize(),
            animation = stackAnimation(fade() + scale())
        ){
            when( val instance = it.instance){
                is Child.SignUp-> SignUp(signUpComponent = instance.signUpComponent)
                is Child.SignIn -> SignIn(signInComponent = instance.signInComponent)
            }
        }
    }
}