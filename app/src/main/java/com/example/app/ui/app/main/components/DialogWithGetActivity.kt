package com.example.app.ui.app.main.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DialogWithGetActivity(activity: String, onAccept: () -> Unit, onDismiss: () -> Unit,) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("New Activity from Partner") },
        text = { Text(activity) },
        confirmButton = {
            Button(onClick = onAccept) {
                Text("Accept")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Decline")
            }
        }
    )
}