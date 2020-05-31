package com.funrisestudio.avengers.core.extensions

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutionException

suspend fun <T> Task<T>.await(): T {
    return withContext(Dispatchers.IO) {
        Tasks.await(this@await)
    }
}

suspend fun <T, R> Task<T?>.execute(transform: (T) -> R): Either<Failure, R> {
    return try {
        val response = await()
        if (response != null && isSuccessful && isComplete)
            Either.Right(transform(response))
        else
            Either.Left(Failure.ServerError)
    } catch (e: ExecutionException) {
        e.printStackTrace()
        Either.Left(Failure.ServerError)
    } catch (e: InterruptedException) {
        e.printStackTrace()
        Either.Left(Failure.ServerError)
    }
}

suspend fun <T> Task<T?>.execute(): Either<Failure, T> {
    return try {
        val response = await()
        if (response != null && isSuccessful && isComplete)
            Either.Right(response)
        else
            Either.Left(Failure.ServerError)
    } catch (e: ExecutionException) {
        e.printStackTrace()
        Either.Left(Failure.ServerError)
    } catch (e: InterruptedException) {
        e.printStackTrace()
        Either.Left(Failure.ServerError)
    }
}