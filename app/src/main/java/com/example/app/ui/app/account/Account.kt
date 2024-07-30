package com.example.app.ui.app.account

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.app.ui.components.AppButton
import com.example.app.ui.components.ChangeTextField
import com.example.app.ui.components.Toast
import com.example.components.app.account.AccountComponent
import com.example.mvi.account.AccountIntent

@Composable
fun Account(
    accountComponent: AccountComponent
) {
    val state by accountComponent.state.subscribeAsState()

    val intent = Intent(Intent.ACTION_PICK)
    intent.type= "image/*"
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri: Uri = result.data?.data ?: return@rememberLauncherForActivityResult
            accountComponent.processIntent(AccountIntent.ChangedImage(imageUri.toString()))
        }
    }

    Column {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Gray, CircleShape)
        ) {
            if (!state.imageYour.isNullOrEmpty()) {
                val painter = rememberAsyncImagePainter(model = state.imageYour)
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile Icon",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
            }

            IconButton(
                onClick = { launcher.launch(intent) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Icon(
                    imageVector = Icons.Filled.CameraAlt,
                    contentDescription = "Change Profile Picture",
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "You together with ${state.namePartner}",
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        ChangeTextField(
            valueChange = state.namePartner,
            label = "Searching partner",
            description = "asdfg123@gmail.com",
            isPassword = false,
            isPasswordVisible = true,
            onChangePasswordVisibility = { /*TODO*/ },
            onValueChange = { newValue ->
                accountComponent.processIntent(AccountIntent.ChangedNamePartner(newValue))
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        AppButton(
            onClick = { accountComponent.processIntent(AccountIntent.InvitePartner) },
            title = "Search"
        )
    }

    state.error?.let { Toast(notice = it) }
}