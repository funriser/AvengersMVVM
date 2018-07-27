package com.funrisestudio.avengers.core.di

import com.funrisestudio.avengers.data.AvengersRepositoryImpl
import com.funrisestudio.avengers.data.source.Firestore
import com.funrisestudio.avengers.domain.AvengersRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun executor (): Executor = Executors.newCachedThreadPool()

    @Provides
    @Singleton
    fun firestoreDB (): Firestore = Firestore(FirebaseFirestore.getInstance())

    @Provides
    @Singleton
    fun avengersRepository (avengersRepositoryImpl: AvengersRepositoryImpl): AvengersRepository = avengersRepositoryImpl

}