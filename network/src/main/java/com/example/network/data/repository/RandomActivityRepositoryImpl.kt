package com.example.network.data.repository

import com.example.network.domain.model.RandomActivity
import com.example.network.domain.repository.RandomActivityRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RandomActivityRepositoryImpl(
    private val httpClient: HttpClient
) : RandomActivityRepository {
    override suspend fun getRandomActivityRepository(): RandomActivity = httpClient
        .get("random").body<RandomActivity>()
}