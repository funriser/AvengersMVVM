package com.funrisestudio.avengers.app.avengerDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funrisestudio.avengers.app.view.AvengerMovieView
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.domain.entity.AvengerMovie
import com.funrisestudio.avengers.domain.interactor.GetMoviesForAvenger

class AvengerDetailViewModel(private val interactor: GetMoviesForAvenger) : ViewModel () {

    val avengerMovies = MutableLiveData<List<AvengerMovieView>> ()

    val failure = MutableLiveData<Failure> ()

    fun getAvengerMovies (avengerId: String) = interactor.invoke(avengerId, viewModelScope) {
        it.either(this::onGetAvengerMoviesError,this::onGetAvengerMoviesSuccess)
    }

    private fun onGetAvengerMoviesSuccess (avengerMovies: List<AvengerMovie>) {
        this.avengerMovies.value = avengerMovies.map { AvengerMovieView (it) }
    }

    private fun onGetAvengerMoviesError (failure: Failure) {
        this.failure.value = failure
    }

}