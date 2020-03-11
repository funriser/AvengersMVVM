package com.funrisestudio.avengers

import android.app.Application
import com.funrisestudio.avengers.app.avengerDetail.AvengerDetailViewModel
import com.funrisestudio.avengers.app.avengerDetail.AvengerDetailsAnimator
import com.funrisestudio.avengers.app.avengerDetail.AvengerMoviesAdapter
import com.funrisestudio.avengers.app.avengers.AvengersAdapter
import com.funrisestudio.avengers.app.avengers.AvengersAnimator
import com.funrisestudio.avengers.app.avengers.AvengersViewModel
import com.funrisestudio.avengers.app.view.AvengerView
import com.funrisestudio.avengers.core.NetworkHandler
import com.funrisestudio.avengers.data.AvengersRepositoryImpl
import com.funrisestudio.avengers.data.source.Firestore
import com.funrisestudio.avengers.domain.AvengersRepository
import com.funrisestudio.avengers.domain.interactor.GetAvengers
import com.funrisestudio.avengers.domain.interactor.GetMoviesForAvenger
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(dataModule, avengersModule, avengerDetailModule))
        }
    }

    private val dataModule = module {
        single { Firestore(FirebaseFirestore.getInstance()) }
        single<AvengersRepository> { AvengersRepositoryImpl(get(), get()) }
        factory { NetworkHandler(androidContext()) }
    }

    private val avengersModule = module {
        factory { AvengersAnimator() }
        factory { AvengersAdapter() }
        factory { GetAvengers(get()) }
        viewModel { AvengersViewModel(get()) }
    }

    private val avengerDetailModule = module {
        factory { GetMoviesForAvenger(get()) }
        factory { AvengerDetailsAnimator() }
        factory { AvengerMoviesAdapter() }
        viewModel { (view: AvengerView) -> AvengerDetailViewModel(get(), view) }
    }

}