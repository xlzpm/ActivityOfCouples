package com.example.app.ui.components

import android.widget.Toast.makeText
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun Toast(
    notice: String
){
    makeText(LocalContext.current, notice, android.widget.Toast.LENGTH_SHORT).show()
}