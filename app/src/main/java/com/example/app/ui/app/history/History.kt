package com.example.app.ui.app.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.app.ui.components.Toast
import com.example.components.app.history.HistoryComponent
import com.example.mvi.history.HistoryIntent

@Composable
fun History(
    historyComponent: HistoryComponent,
){
    val state by historyComponent.state.subscribeAsState()

    Column{
        Button(
            onClick = {historyComponent.processIntent(HistoryIntent.LoadHistory)},
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ){
            Text(
                text = "Load History",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(5.dp)
            )
        }
        state.activities.forEach{ (activity, date) ->
            Text(
                text = "$date: $activity",
                fontSize = 16.sp,
                modifier = Modifier.padding(5.dp)
            )
        }
    }
    state.error?.let { Toast(notice = it) }
}