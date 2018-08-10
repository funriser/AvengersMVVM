package com.funrisestudio.avengers.app.avengerDetail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.funrisestudio.avengers.app.view.AvengerMovieView
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.domain.entity.AvengerMovie
import com.funrisestudio.avengers.domain.interactor.GetMoviesForAvenger
import javax.inject.Inject

class AvengerDetailViewModel @Inject constructor (private val interactor: GetMoviesForAvenger) : ViewModel () {

    val avengerMovies = MutableLiveData<List<AvengerMovieView>> ()

    val failure = MutableLiveData<Failure> ()

    fun getAvengerMovies (avengerId: String) = interactor.invoke(avengerId) {
        it.either(this::onGetAvengerMoviesError,this::onGetAvengerMoviesSuccess)
    }

    private fun onGetAvengerMoviesSuccess (avengerMovies: List<AvengerMovie>) {
        this.avengerMovies.value = avengerMovies.map { AvengerMovieView (it) }
    }

    private fun onGetAvengerMoviesError (failure: Failure) {
        this.failure.value = failure
    }

}