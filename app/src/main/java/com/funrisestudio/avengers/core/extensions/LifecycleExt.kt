package com.funrisestudio.avengers.core.extensions

import androidx.lifecycle.*
import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

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