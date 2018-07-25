package com.funrisestudio.avengers.app.avengers

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.domain.UseCase
import com.funrisestudio.avengers.domain.entity.Avenger
import com.funrisestudio.avengers.domain.interactor.GetAvengers
import javax.inject.Inject

class AvengersViewModel @Inject constructor (private val getAvengers: GetAvengers): ViewModel () {

    val avengers = MutableLiveData<List<Avenger>> ()

    val failure = MutableLiveData<Failure> ()

    fun getAvengers () = getAvengers.invoke(UseCase.None()) {
        it.either(this::onGetAvengersError, this::onGetAvengersSuccess)
    }

    private fun onGetAvengersSuccess (avengersList: List<Avenger>) {
        avengers.value = avengersList
    }

    private fun onGetAvengersError (failure: Failure) {
        this.failure.value = failure
    }

}