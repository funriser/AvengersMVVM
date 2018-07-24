package com.funrisestudio.avengers.core.di.avengers

import com.funrisestudio.avengers.app.MainActivity
import dagger.Subcomponent

@Subcomponent (modules = [AvengersModule::class])
@AvengersScope
interface AvengersComponent {

    fun inject (mainActivity: MainActivity)

}