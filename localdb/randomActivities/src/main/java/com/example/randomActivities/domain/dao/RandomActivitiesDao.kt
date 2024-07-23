package com.example.randomActivities.domain.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.randomActivities.domain.model.RandomActivitiesEntity

@Dao
interface RandomActivitiesDao {
    @Query("SELECT * FROM random_activities")
    suspend fun getRandomActivities(): List<RandomActivitiesEntity>

    @Insert
    suspend fun addRandomActivity(randomActivityEntity: RandomActivitiesEntity)
}