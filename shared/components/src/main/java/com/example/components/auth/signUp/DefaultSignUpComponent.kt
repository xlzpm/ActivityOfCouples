package com.example.components.auth.signUp

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.example.auth.domain.repository.signUp.SignUpRepository
import com.example.mvi.signIn.SignInIntent
import com.example.mvi.signUp.SignUpIntent
import com.example.mvi.signUp.SignUpState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception


class DefaultSignUpComponent(
    private val componentContext: ComponentContext,
    private val onPrevClick: () -> Unit,
    private val onSignUpClick: () -> Unit,
    private val signUpRepository: SignUpRepository
): SignUpComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        stateKeeper.consume(SIGN_IN_COMPONENT, SignUpState.serializer()) ?: SignUpState()
    )
    override val state: Value<SignUpState> = _state

    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun processIntent(intent: SignUpIntent) {
        when(intent){
            is SignUpIntent.SignUp -> signUp()
            is SignUpIntent.SignInScreen -> onPrevClicked()
            is SignUpIntent.EmailChanged -> _state.update { it.copy(email = intent.email) }
            is SignUpIntent.PasswordChanged -> _state.update { it.copy(password = intent.password) }
            is SignUpIntent.IsPasswordVisibleChanged -> _state.update {
                it.copy(isPasswordVisible = intent.isPasswordVisibleChanged)
            }
        }
    }

    override fun onPrevClicked() {
        onPrevClick()
    }

    private fun signUp(){
        coroutineScope.launch {
            try {
                signUpRepository.signUp(_state.value.email, _state.value.password)
                onSignUpClick()
            } catch (e: Exception){
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    companion object{
        const val SIGN_IN_COMPONENT = "SIGN_IN_COMPONENT"
    }
}