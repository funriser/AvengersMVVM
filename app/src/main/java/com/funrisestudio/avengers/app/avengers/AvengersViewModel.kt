package com.funrisestudio.avengers.app.avengers

import androidx.lifecycle.*
import com.funrisestudio.avengers.app.view.AvengerView
import com.funrisestudio.avengers.core.SingleLiveEvent
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.domain.UseCase
import com.funrisestudio.avengers.domain.entity.Avenger
import com.funrisestudio.avengers.domain.interactor.GetAvengers

class AvengersViewModel(private val getAvengers: GetAvengers) : ViewModel() {

    private val _avengers = MutableLiveData<List<Avenger>>()
    val avengers: LiveData<List<AvengerView>> = _avengers.map { result ->
        result.map { AvengerView(it) }
    }

    private val _failure = MutableLiveData<Failure>()
    val failure: LiveData<Failure> = _failure

    private val _progress = SingleLiveEvent<Boolean>()
    val progress: LiveData<Boolean> = _progress

    init {
        loadAvengers()
    }

    fun loadAvengers() {
        _progress.value = true
        getAvengers.invoke(UseCase.None(), viewModelScope) {
            it.either(::onGetAvengersError, ::onGetAvengersSuccess)
        }
    }

    private fun onGetAvengersSuccess(avengersList: List<Avenger>) {
        _progress.value = false
        _avengers.value = avengersList
    }

    private fun onGetAvengersError(failure: Failure) {
        _progress.value = false
        this._failure.value = failure
    }

}