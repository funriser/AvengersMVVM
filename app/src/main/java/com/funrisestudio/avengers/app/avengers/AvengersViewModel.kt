package com.funrisestudio.avengers.app.avengers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funrisestudio.avengers.app.view.AvengerView
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.domain.UseCase
import com.funrisestudio.avengers.domain.entity.Avenger
import com.funrisestudio.avengers.domain.interactor.GetAvengers

class AvengersViewModel(private val getAvengers: GetAvengers): ViewModel () {

    val avengers = MutableLiveData<List<AvengerView>>()

    val failure = MutableLiveData<Failure> ()

    fun getAvengers() {
        getAvengers.invoke(UseCase.None(), viewModelScope) {
            it.either(::onGetAvengersError, ::onGetAvengersSuccess)
        }
    }

    private fun onGetAvengersSuccess (avengersList: List<Avenger>) {
        avengers.value = avengersList.map { AvengerView(it) }
    }

    private fun onGetAvengersError (failure: Failure) {
        this.failure.value = failure
    }

}