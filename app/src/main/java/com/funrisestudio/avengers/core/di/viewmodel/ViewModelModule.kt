package com.funrisestudio.avengers.core.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.funrisestudio.avengers.app.avengerDetail.AvengerDetailViewModel
import com.funrisestudio.avengers.app.avengers.AvengersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun viewModelFactory (viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey (AvengersViewModel::class)
    abstract fun avengersViewModel (avengersViewModel: AvengersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey (AvengerDetailViewModel::class)
    abstract fun avengerDetailsViewModel (avengersDetailViewModel: AvengerDetailViewModel): ViewModel

}