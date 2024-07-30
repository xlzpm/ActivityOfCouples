package com.example.app.ui.app.main.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DialogWithSendActivity(partnerActivity: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Activity Sent to Partner") },
        text = { Text(partnerActivity) },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}