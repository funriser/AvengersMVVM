package com.funrisestudio.avengers.core.di

import com.funrisestudio.avengers.app.MainActivity
import com.funrisestudio.avengers.app.avengerDetail.AvengerDetailActivity
import com.funrisestudio.avengers.core.di.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Component (modules = [AndroidModule::class, ViewModelModule::class, DataModule::class])
@Singleton
interface AppComponent {
    fun inject (activity: MainActivity)
    fun inject (activity: AvengerDetailActivity)
}