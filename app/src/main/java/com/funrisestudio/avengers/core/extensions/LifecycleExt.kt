package com.funrisestudio.avengers.core.extensions

import androidx.lifecycle.*
import com.funrisestudio.avengers.core.exception.Failure

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
        liveData.observe(this, Observer(body))

fun <L : LiveData<Failure>> LifecycleOwner.failure(liveData: L, body: (Failure?) -> Unit) =
        liveData.observe(this, Observer(body))

fun MutableLiveData<*>.pushOrUpdateIfNull(action: () -> Unit) {
    val current = value
    if (current != null) {
        value = current
    } else {
        action.invoke()
    }
}