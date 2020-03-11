package com.funrisestudio.avengers.app.avengerDetail

import androidx.lifecycle.*
import com.funrisestudio.avengers.app.view.AvengerMovieView
import com.funrisestudio.avengers.app.view.AvengerView
import com.funrisestudio.avengers.core.SingleLiveEvent
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.domain.entity.AvengerMovie
import com.funrisestudio.avengers.domain.interactor.GetMoviesForAvenger

class AvengerDetailViewModel(
        private val interactor: GetMoviesForAvenger,
        private val avengerView: AvengerView
) : ViewModel() {

    val avengerDetails: LiveData<AvengerView>

    private val _avengerMovies = MutableLiveData<List<AvengerMovie>>()
    val avengerMovies: LiveData<List<AvengerMovieView>> = _avengerMovies.map { result ->
        result.map { AvengerMovieView(it) }
    }

    private val _failure = MutableLiveData<Failure>()
    val failure: LiveData<Failure> = _failure

    private val _progress = SingleLiveEvent<Boolean>()
    val progress: LiveData<Boolean> = _progress

    init {
        avengerDetails = MutableLiveData(avengerView)
        loadAvengerMovies(avengerView.id)
    }

    fun loadAvengerMovies() {
        loadAvengerMovies(avengerView.id)
    }

    private fun loadAvengerMovies(avengerId: String) {
        _progress.value = true
        interactor.invoke(avengerId, viewModelScope) {
            it.either(this::onGetAvengerMoviesError, this::onGetAvengerMoviesSuccess)
        }
    }

    private fun onGetAvengerMoviesSuccess(avengerMovies: List<AvengerMovie>) {
        _progress.value = false
        _avengerMovies.value = avengerMovies
    }

    private fun onGetAvengerMoviesError(failure: Failure) {
        _progress.value = false
        _failure.value = failure
    }

}