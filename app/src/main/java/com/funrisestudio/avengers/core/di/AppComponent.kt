package com.funrisestudio.avengers.core.di

import com.funrisestudio.avengers.app.avengers.AvengersFragment
import com.funrisestudio.avengers.core.di.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Component (modules = [AndroidModule::class, ViewModelModule::class, DataModule::class])
@Singleton
interface AppComponent {

    fun inject (avengersFragment: AvengersFragment)

}