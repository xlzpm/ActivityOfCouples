package com.example.network.di

import com.example.network.core.Const
import com.example.network.data.repository.RandomActivityRepositoryImpl
import com.example.network.domain.repository.RandomActivityRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule: Module = module {
    single<HttpClient>{
        HttpClient(CIO){
            defaultRequest {
                url(Const.BORED_API)
                contentType(ContentType.Application.Json)
            }
        }
    }

    single<RandomActivityRepository> {
        RandomActivityRepositoryImpl(
            httpClient = get()
        )
    }
}