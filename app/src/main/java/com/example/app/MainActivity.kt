package com.example.app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.arkivanov.decompose.defaultComponentContext
import com.example.app.ui.root.Root
import com.example.app.ui.theme.TrainingTheme
import com.example.auth.domain.repository.signIn.SignInRepository
import com.example.auth.domain.repository.signUp.SignUpRepository
import com.example.components.root.DefaultRootComponent
import com.example.components.root.DefaultRootComponent.Companion.ROOT_APP_KEY
import com.example.firestore.domain.repository.ActivityRepo
import com.example.firestore.domain.repository.ConnectUserRepo
import com.example.firestore.domain.repository.HistoryRepo
import com.example.network.domain.repository.RandomActivityRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.koin.android.ext.android.getKoin
import org.koin.core.KoinApplication.Companion.init

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = this.getSharedPreferences(ROOT_APP_KEY, Context.MODE_PRIVATE)

        val rootComponent = DefaultRootComponent(
            componentContext = defaultComponentContext(),
            sharedPref = sharedPref,
            signInRepository = getKoin().get<SignInRepository>(),
            signUpRepository = getKoin().get<SignUpRepository>(),
            activityRepo = getKoin().get<ActivityRepo>(),
            randomActivityRepository = getKoin().get<RandomActivityRepository>(),
            historyRepo = getKoin().get<HistoryRepo>(),
            connectUserRepo = getKoin().get<ConnectUserRepo>(),
            firebaseAuth = getKoin().get<FirebaseAuth>(),
            firestore = getKoin().get<FirebaseFirestore>()
        )

        setContent {
            TrainingTheme {
                Root(
                    rootComponent = rootComponent
                )
            }
        }
    }
}