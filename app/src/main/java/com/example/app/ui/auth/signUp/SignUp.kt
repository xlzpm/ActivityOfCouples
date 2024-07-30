package com.example.app.ui.auth.signUp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.app.ui.components.AppButton
import com.example.app.ui.components.ChangeTextField
import com.example.app.ui.components.Toast
import com.example.components.auth.signUp.SignUpComponent
import com.example.mvi.signUp.SignUpIntent

@Composable
fun SignUp(
    signUpComponent: SignUpComponent
){
    val state by signUpComponent.state.subscribeAsState()

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
            onValueChange = { newValue ->
                signUpComponent.processIntent(SignUpIntent.EmailChanged(newValue))
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        ChangeTextField(
            valueChange = state.password,
            label = "Password",
            description = "********",
            isPassword = true,
            isPasswordVisible = state.isPasswordVisible,
            onChangePasswordVisibility = {
                signUpComponent
                    .processIntent(SignUpIntent.IsPasswordVisibleChanged(!state.isPasswordVisible))
            },
            onValueChange = { newValue ->
                signUpComponent.processIntent(SignUpIntent.PasswordChanged(newValue))
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        AppButton(
            onClick = {signUpComponent.processIntent(SignUpIntent.SignUp)},
            title = "SignIn"
        )
        Text(
            text = "If you have account, click this inscription",
            modifier = Modifier.clickable(onClick = {
                signUpComponent.processIntent(SignUpIntent.SignInScreen)
            })
        )
    }
    state.error?.let { Toast(notice = it) }
}