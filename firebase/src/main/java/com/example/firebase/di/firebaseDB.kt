package com.example.firebase.di

import com.example.auth.domain.repository.signIn.SignInRepository
import com.example.auth.domain.repository.signUp.SignUpRepository
import com.example.firestore.data.repository.ActivityRepoImpl
import com.example.firestore.data.repository.ConnectUserRepoImpl
import com.example.firestore.data.repository.HistoryRepoImpl
import com.example.firestore.domain.repository.ActivityRepo
import com.example.firestore.domain.repository.ConnectUserRepo
import com.example.firestore.domain.repository.HistoryRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.module.Module
import org.koin.dsl.module

val firebaseDB: Module = module {
    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }

    single<FirebaseFirestore> {
        FirebaseFirestore.getInstance()
    }

    single<SignInRepository> {
        com.example.auth.data.repository.signIn.SignInRepositoryImpl(
            firebaseAuth = get()
        )
    }


    single<SignUpRepository> {
        com.example.auth.data.repository.signUp.SignUpRepositoryImpl(
            firebaseAuth = get()
        )
    }

    single<ConnectUserRepo> {
        ConnectUserRepoImpl(
            firestore = get()
        )
    }

    single<ActivityRepo> {
        ActivityRepoImpl(
            firestore = get()
        )
    }

    single<HistoryRepo> {
        HistoryRepoImpl(
            firestore = get()
        )
    }
}