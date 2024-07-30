package com.example.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppButton(
    onClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
){
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(6.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(5.dp)
        )
    }
}