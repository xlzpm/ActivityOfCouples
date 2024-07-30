package com.example.components.auth.signIn

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.example.auth.domain.repository.signIn.SignInRepository
import com.example.mvi.signIn.SignInIntent
import com.example.mvi.signIn.SignInState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception

class DefaultSignInComponent(
    private val componentContext: ComponentContext,
    private val onBackClick: () -> Unit,
    private val onSignInClick: () -> Unit,
    private val repository: SignInRepository
): SignInComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        stateKeeper.consume(SIGN_IN_COMPONENT, SignInState.serializer()) ?: SignInState()
    )
    override val state: Value<SignInState> = _state

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun processIntent(intent: SignInIntent) {
        when(intent){
            is SignInIntent.SignIn -> signIn()
            is SignInIntent.SignUpScreen -> onBackClicked()
            is SignInIntent.EmailChanged -> _state.update { it.copy(email = intent.email) }
            is SignInIntent.PasswordChanged -> _state.update { it.copy(password = intent.password) }
            is SignInIntent.IsPasswordVisibleChanged -> _state.update {
                it.copy(isPasswordVisible = intent.isPasswordVisibleChanged)
            }
        }
    }

    override fun onBackClicked() {
        onBackClick()
    }

    private fun signIn(){
        coroutineScope.launch {
            try {
                repository.signIn(_state.value.email, _state.value.password)
                onSignInClick()
            } catch (e: Exception){
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    companion object{
        const val SIGN_IN_COMPONENT = "SIGN_IN_COMPONENT"
    }
}