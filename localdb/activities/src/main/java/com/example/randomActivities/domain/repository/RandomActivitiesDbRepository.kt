package com.example.randomActivities.domain.repository

import com.example.network.domain.model.RandomActivity
import com.example.randomActivities.domain.model.RandomActivitiesEntity

interface RandomActivitiesDbRepository {
    suspend fun getRandomActivities(): List<RandomActivitiesEntity>?

    suspend fun getRandomActivity(): RandomActivitiesEntity
}