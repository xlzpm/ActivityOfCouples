package com.example.components.app.account

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.example.firestore.domain.repository.ConnectUserRepo
import com.example.mvi.account.AccountIntent
import com.example.mvi.account.AccountState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class DefaultAccountComponent(
    private val componentContext: ComponentContext,
    private val connectUserRepo: ConnectUserRepo,
) : AccountComponent, ComponentContext by componentContext {
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _state = MutableValue(
        stateKeeper.consume(ACCOUNT_COMPONENT, AccountState.serializer()) ?: AccountState()
    )
    override val state: Value<AccountState> = _state

    override fun processIntent(accountIntent: AccountIntent) =
        when(accountIntent){
            is AccountIntent.InvitePartner -> invitePartner(_state.value.namePartner)
            is AccountIntent.ChangedNamePartner -> _state.update { it.copy(namePartner = accountIntent.name) }
            is AccountIntent.ChangedImage -> changeProfilePhoto(accountIntent.image.toUri())
        }

    private fun invitePartner(namePartner: String) {
        coroutineScope.launch {
            try {
                connectUserRepo
                    .createPair(_state.value.myName.toString(), namePartner)
                _state.update { it.copy(namePartner = namePartner) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    private fun changeProfilePhoto(imageUri: Uri){
        coroutineScope.launch {
            try {
                val context = componentContext as Context
                val file = saveImageToInternalStorage(context, imageUri)
                _state.update { it.copy(imageYour = file.absolutePath) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    private fun saveImageToInternalStorage(context: Context, imageUri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(imageUri)
        val file = File(context.filesDir, "profile_photo.jpg")
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        return file
    }


    companion object{
        const val ACCOUNT_COMPONENT = "ACCOUNT_COMPONENT"
    }
}