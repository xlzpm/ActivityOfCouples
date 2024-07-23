package com.example.randomActivities.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.network.domain.model.RandomActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "random_activities")
data class RandomActivitiesEntity(
    @PrimaryKey
    val key: String,
    val activity: String,
    val dateTime: String
)

fun RandomActivity.toEntity(): RandomActivitiesEntity{
    return RandomActivitiesEntity(
        key = key,
        activity = activity,
        dateTime = getCurrentDate()
    )
}

private fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(Date())
}

