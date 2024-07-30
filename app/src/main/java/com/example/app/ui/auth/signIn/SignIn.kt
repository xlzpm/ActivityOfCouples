package com.example.app.ui.auth.signIn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.app.ui.components.AppButton
import com.example.app.ui.components.ChangeTextField
import com.example.app.ui.components.Toast
import com.example.components.auth.signIn.SignInComponent
import com.example.mvi.signIn.SignInIntent

@Composable
fun SignIn(
    signInComponent: SignInComponent
){
    val state by signInComponent.state.subscribeAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ){
        ChangeTextField(
            valueChange = state.email,
            label = "Email",
            description = "qwerty123@yandex.ru",
            isPassword = false,
            isPasswordVisible = true,
            onChangePasswordVisibility = {},
            onValueChange = {
                signInComponent.processIntent(SignInIntent.EmailChanged(state.email))
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        ChangeTextField(
            valueChange = state.email,
            label = "Password",
            description = "********",
            isPassword = true,
            isPasswordVisible = state.isPasswordVisible,
            onChangePasswordVisibility = {
                signInComponent
                    .processIntent(SignInIntent.IsPasswordVisibleChanged(state.isPasswordVisible))
            },
            onValueChange = {
                signInComponent.processIntent(SignInIntent.PasswordChanged(state.password))
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        AppButton(
            onClick = {signInComponent.processIntent(SignInIntent.SignIn)},
            title = "SignIn"
        )
    }
    state.error?.let { Toast(notice = it) }
}