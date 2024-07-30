package com.example.network.domain.repository

import com.example.network.domain.model.RandomActivity

interface RandomActivityRepository {
    suspend fun getRandomActivity(): RandomActivity
}