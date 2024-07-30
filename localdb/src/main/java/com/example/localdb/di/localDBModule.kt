package com.example.localdb.di

import androidx.room.Room
import com.example.localdb.db.CoupleAppDatabase
import com.example.randomActivities.data.repository.RandomActivitiesDbRepositoryImpl
import com.example.randomActivities.domain.dao.RandomActivitiesDao
import com.example.randomActivities.domain.repository.RandomActivitiesDbRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val localDBModule: Module = module {
    single<CoupleAppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            CoupleAppDatabase::class.java,
            "couple_app_db"
        ).build()
    }

    single<RandomActivitiesDao> {
        get<CoupleAppDatabase>().RandomActivitiesDao()
    }

    single<RandomActivitiesDbRepository> {
        RandomActivitiesDbRepositoryImpl(
            randomActivityDao = get(),
            randomActivityRepository = get()
        )
    }
}