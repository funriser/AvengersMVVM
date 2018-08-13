package com.funrisestudio.avengers.core.di

import com.funrisestudio.avengers.app.avengers.AvengersActivity
import com.funrisestudio.avengers.app.avengerDetail.AvengerDetailActivity
import com.funrisestudio.avengers.core.base.BaseActivity
import com.funrisestudio.avengers.core.di.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Component (modules = [AndroidModule::class, ViewModelModule::class, DataModule::class])
@Singleton
interface AppComponent {
    fun inject (activity: BaseActivity)
    fun inject (activity: AvengersActivity)
    fun inject (activity: AvengerDetailActivity)
}