package com.example.components.root

import android.content.SharedPreferences
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.auth.domain.repository.signIn.SignInRepository
import com.example.auth.domain.repository.signUp.SignUpRepository
import com.example.components.app.AppComponent
import com.example.components.app.DefaultAppComponent
import com.example.components.auth.AuthComponent
import com.example.components.auth.DefaultAuthComponent
import com.example.components.root.RootComponent.Child
import com.example.firestore.domain.repository.ActivityRepo
import com.example.firestore.domain.repository.ConnectUserRepo
import com.example.firestore.domain.repository.HistoryRepo
import com.example.network.domain.repository.RandomActivityRepository
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    private val componentContext: ComponentContext,
    private val sharedPref: SharedPreferences,
    private val signUpRepository: SignUpRepository,
    private val signInRepository: SignInRepository,
    private val activityRepo: ActivityRepo,
    private val randomActivityRepository: RandomActivityRepository,
    private val historyRepo: HistoryRepo,
    private val connectUserRepo: ConnectUserRepo
) : RootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()

    override val child: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration =
            if (sharedPref.getBoolean(FIRST_RUN, true)){
                Config.Auth
            } else {
                Config.App
            },
            handleBackButton = true,
            childFactory = ::child
        )

    private fun child(config: Config, componentContext: ComponentContext): Child =
        when(config){
            is Config.Auth -> Child.Auth(authComponent = authComponent(componentContext))
            is Config.App -> Child.App(appComponent = appComponent(componentContext))
        }

    private fun authComponent(componentContext: ComponentContext): AuthComponent =
        DefaultAuthComponent(
            componentContext = componentContext,
            onSignClick = {
                navigation.push(Config.App)
                sharedPref.edit().putBoolean(FIRST_RUN, false).apply()
            },
            signUpRepository = signUpRepository,
            signInRepository = signInRepository
        )

    private fun appComponent(componentContext: ComponentContext): AppComponent =
        DefaultAppComponent(
            componentContext = componentContext,
            activityRepo = activityRepo,
            randomActivityRepository = randomActivityRepository,
            historyRepo = historyRepo,
            connectUserRepo = connectUserRepo
        )

    @Serializable
    sealed interface Config{
        data object Auth: Config
        data object App: Config
    }

    companion object {
        const val ROOT_APP_KEY = "ROOT_APP_KEY"
        const val FIRST_RUN = "FIRST_RUN"
    }
}