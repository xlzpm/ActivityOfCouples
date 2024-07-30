package com.example.app.ui.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalActivity
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.example.app.domain.model.BottomNavigationItem
import com.example.app.ui.app.account.Account
import com.example.app.ui.app.history.History
import com.example.app.ui.app.main.Main
import com.example.components.app.AppComponent
import com.example.components.app.AppComponent.Child

@Composable
fun App(
    appComponent: AppComponent,
){
    var selectedIcon by rememberSaveable { mutableIntStateOf(0) }
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White
            ) {
               items.forEachIndexed{ index, item ->
                    NavigationBarItem(
                        selected = selectedIcon == index,
                        onClick = {
                            selectedIcon = index
                            when (index) {
                                0 -> appComponent.onMainClicked()
                                1 -> appComponent.onHistoryClicked()
                                else -> appComponent.onAccountClicked()
                            } },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Black),

                    )
               }
            }
        }
    ) { paddingValues ->  paddingValues.calculateBottomPadding()
        Children(
            stack = appComponent.child,
            modifier = Modifier.fillMaxSize(),
            animation = stackAnimation(slide() + fade() + scale())
        ) {
           when( val instance = it.instance){
               is Child.Main -> Main(
                   mainComponent = instance.mainComponent,
               )
               is Child.History -> History(
                   historyComponent = instance.historyComponent,
               )
               is Child.Account -> Account(accountComponent = instance.accountComponent)
           }
        }
    }
}

val items = listOf(
    BottomNavigationItem(
        title = "Main",
        icon = Icons.Filled.LocalActivity
    ),
    BottomNavigationItem(
        title = "History",
        icon = Icons.Filled.History
    ),
    BottomNavigationItem(
        title = "Account",
        icon = Icons.Filled.AccountCircle
    )
)

