package com.funrisestudio.avengers.domain

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import kotlinx.coroutines.*

abstract class UseCase<out Type, in Params> where Type : Any {

    open val dispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(
        params: Params,
        coroutineScope: CoroutineScope,
        then: (Either<Failure, Type>) -> Unit = {}
    ) {
        coroutineScope.launch {
            val res = withContext(dispatcher) {
                run(params)
            }
            then.invoke(res)
        }
    }

    class None

}
