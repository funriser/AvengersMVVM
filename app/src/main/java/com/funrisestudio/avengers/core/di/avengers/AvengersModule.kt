package com.funrisestudio.avengers.core.di.avengers

import com.funrisestudio.avengers.domain.AvengersRepository
import com.funrisestudio.avengers.domain.interactor.GetAvengers
import dagger.Module
import dagger.Provides

@Module
class AvengersModule {

    @Provides
    @AvengersScope
    fun getAvengersInteractor (repository: AvengersRepository): GetAvengers = GetAvengers(repository)

}