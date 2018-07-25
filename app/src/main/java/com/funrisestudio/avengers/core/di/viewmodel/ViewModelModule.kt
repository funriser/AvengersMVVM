package com.funrisestudio.avengers.core.di.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.funrisestudio.avengers.app.avengers.AvengersViewModel
import com.funrisestudio.avengers.core.di.SampleViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun viewModelFactory (viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey (AvengersViewModel::class)
    abstract fun avengersViewModel (avengersViewModel: AvengersViewModel): ViewModel

}