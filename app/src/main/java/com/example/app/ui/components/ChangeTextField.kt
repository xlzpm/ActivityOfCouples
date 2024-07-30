package com.example.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChangeTextField(
    valueChange: String,
    label: String,
    description: String,
    isPassword: Boolean,
    isPasswordVisible: Boolean,
    onChangePasswordVisibility: () -> Unit,
    onValueChange: (String) -> Unit,
){
    Column {
        TextField(
            value = valueChange,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                )
            },
            placeholder = {
                Text(
                    text = description,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(1.dp)
                )
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                if (isPassword) {
                    val image =
                        if (isPasswordVisible) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                    val descriptionIcon =
                        if (isPasswordVisible) "Hide Password" else "Show Password"

                    IconButton(
                        onClick = onChangePasswordVisibility
                    ) {
                        Icon(
                            imageVector = image,
                            contentDescription = descriptionIcon
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
        )
    }
}
