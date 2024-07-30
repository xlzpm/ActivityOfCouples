package com.example.components.app

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.example.components.app.AppComponent.Child
import com.example.components.app.account.AccountComponent
import com.example.components.app.account.DefaultAccountComponent
import com.example.components.app.history.DefaultHistoryComponent
import com.example.components.app.history.HistoryComponent
import com.example.components.app.main.DefaultMainComponent
import com.example.components.app.main.MainComponent
import com.example.firestore.domain.repository.ActivityRepo
import com.example.firestore.domain.repository.ConnectUserRepo
import com.example.firestore.domain.repository.HistoryRepo
import com.example.network.domain.repository.RandomActivityRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.serialization.Serializable

class DefaultAppComponent(
    private val componentContext: ComponentContext,
    private val activityRepo: ActivityRepo,
    private val randomActivityRepository: RandomActivityRepository,
    private val historyRepo: HistoryRepo,
    private val connectUserRepo: ConnectUserRepo,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): AppComponent, ComponentContext by componentContext{
    private val navigation = StackNavigation<Config>()

    override val child: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Main,
            handleBackButton = true,
            childFactory = ::child
        )

    private fun child(config: Config, componentContext: ComponentContext): Child =
        when(config){
            Config.Main -> Child.Main(mainComponent = mainComponent(componentContext))
            Config.History -> Child.History(historyComponent = historyComponent(componentContext))
            Config.Account -> Child.Account(accountComponent = accountComponent(componentContext))
        }

    private fun mainComponent(componentContext: ComponentContext): MainComponent =
        DefaultMainComponent(
            componentContext = componentContext,
            activityRepo = activityRepo,
            randomActivityRepository = randomActivityRepository,
            firestore = firestore,
            firebaseAuth = firebaseAuth
        )

    private fun historyComponent(componentContext: ComponentContext): HistoryComponent =
        DefaultHistoryComponent(
            componentContext = componentContext,
            historyRepo = historyRepo,
            firebaseAuth = firebaseAuth
        )

    private fun accountComponent(componentContext: ComponentContext): AccountComponent =
        DefaultAccountComponent(
            componentContext = componentContext,
            connectUserRepo = connectUserRepo
        )

    override fun onMainClicked() {
        navigation.replaceAll(Config.Main)
    }

    override fun onHistoryClicked() {
        navigation.replaceAll(Config.History)
    }

    override fun onAccountClicked() {
        navigation.replaceAll(Config.Account)
    }

    @Serializable
    sealed interface Config{
        data object Main: Config
        data object History: Config
        data object Account: Config
    }
}