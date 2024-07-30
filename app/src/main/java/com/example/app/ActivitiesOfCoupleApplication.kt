package com.example.app

import android.app.Application
import com.example.firebase.di.firebaseDB
import com.example.localdb.di.localDBModule
import com.example.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ActivitiesOfCoupleApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ActivitiesOfCoupleApplication)
            modules(listOf(firebaseDB, localDBModule, networkModule))
        }
    }
}