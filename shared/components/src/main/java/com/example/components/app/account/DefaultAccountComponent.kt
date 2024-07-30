package com.example.components.app.account

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.example.firestore.domain.repository.ConnectUserRepo
import com.example.mvi.account.AccountIntent
import com.example.mvi.account.AccountState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class DefaultAccountComponent(
    private val componentContext: ComponentContext,
    private val connectUserRepo: ConnectUserRepo,
    private val context: Context,
    private val firebaseAuth: FirebaseAuth
) : AccountComponent, ComponentContext by componentContext {
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _state = MutableValue(
        stateKeeper.consume(ACCOUNT_COMPONENT, AccountState.serializer()) ?: AccountState()
    )
    override val state: Value<AccountState> = _state

    override fun processIntent(accountIntent: AccountIntent) =
        when(accountIntent){
            is AccountIntent.InvitePartner -> invitePartner(_state.value.namePartner)
            is AccountIntent.ChangedNamePartner -> _state.update {
                it.copy(namePartner = accountIntent.name)
            }
            is AccountIntent.ChangedImage -> changeProfilePhoto(
                context, accountIntent.image.toUri()
            )
        }

    private fun invitePartner(namePartner: String) {
        coroutineScope.launch {
            try {
                connectUserRepo
                    .createPair(firebaseAuth.currentUser?.email.orEmpty(), namePartner)
                _state.update { it.copy(namePartner = namePartner) }
            } catch (e: Exception) {
                Log.e("DefaultAccountComponent", e.message ?: "Unknown fucking shit")
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    private fun changeProfilePhoto(context: Context, imageUri: Uri){
        coroutineScope.launch {
            try {
                val file = saveImageToInternalStorage(context, imageUri)
                _state.update { it.copy(imageYour = file?.toUri().toString()) }
            } catch (e: Exception) {
                Log.e("DefaultAccountComponent", e.message ?: "Unknown fucking shit")
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    private fun saveImageToInternalStorage(context: Context, imageUri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val file = File(context.filesDir, "profile_photo.jpg")
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            file
        } catch (e: Exception) {
            Log.e("DefaultAccountComponent", e.message ?: "Unknown error")
            null
        }
    }


    companion object{
        const val ACCOUNT_COMPONENT = "ACCOUNT_COMPONENT"
    }
}