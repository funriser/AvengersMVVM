package com.funrisestudio.avengers.core.di

import com.funrisestudio.avengers.data.AvengersRepositoryImpl
import com.funrisestudio.avengers.data.source.Firestore
import com.funrisestudio.avengers.domain.AvengersRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun avengersRepository (avengersRepositoryImpl: AvengersRepositoryImpl): AvengersRepository = avengersRepositoryImpl

    @Provides
    @Singleton
    fun firestoreDB (): Firestore = Firestore(FirebaseFirestore.getInstance())

}