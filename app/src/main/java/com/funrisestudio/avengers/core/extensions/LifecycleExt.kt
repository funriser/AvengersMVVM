package com.funrisestudio.avengers.core.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.funrisestudio.avengers.core.exception.Failure

fun <T : Any, L : LiveData<T>> Fragment.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer(body))
}

fun <L : LiveData<Failure>> Fragment.failure(liveData: L, body: (Failure?) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer(body))
}