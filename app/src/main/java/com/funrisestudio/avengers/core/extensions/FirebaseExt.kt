package com.funrisestudio.avengers.core.extensions

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> Task<T>.await(): T {
    return withContext(Dispatchers.IO) {
        Tasks.await(this@await)
    }
}