package com.example.components.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.example.auth.domain.repository.signIn.SignInRepository
import com.example.auth.domain.repository.signUp.SignUpRepository
import com.example.components.auth.AuthComponent.*
import com.example.components.auth.signIn.DefaultSignInComponent
import com.example.components.auth.signIn.SignInComponent
import com.example.components.auth.signUp.DefaultSignUpComponent
import com.example.components.auth.signUp.SignUpComponent
import kotlinx.serialization.Serializable

class DefaultAuthComponent(
    private val componentContext: ComponentContext,
    private val onSignClick: () -> Unit,
    private val signUpRepository: SignUpRepository,
    private val signInRepository: SignInRepository
) : AuthComponent, ComponentContext by componentContext{
    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.SignUp,
            handleBackButton = true,
            childFactory = ::child
        )

    private fun child(config: Config, componentContext: ComponentContext): Child =
        when (config){
            Config.SignUp -> Child.SignUp(signUpComponent = signUpComponent(componentContext))
            Config.SignIn -> Child.SignIn(signInComponent = signInComponent(componentContext))
        }

    private fun signUpComponent(component: ComponentContext): SignUpComponent =
        DefaultSignUpComponent(
            componentContext = component,
            onPrevClick = { navigation.push(Config.SignIn) },
            onSignUpClick = {navigation.push(Config.SignIn)},
            signUpRepository = signUpRepository
        )

    private fun signInComponent(component: ComponentContext): SignInComponent =
        DefaultSignInComponent(
            componentContext = component,
            onSignInClick = onSignClick,
            onBackClick = {navigation.replaceAll(Config.SignUp)},
            repository = signInRepository
        )

    @Serializable
    private sealed interface Config{
        @Serializable
        data object SignIn: Config
        @Serializable
        data object SignUp: Config
    }
}