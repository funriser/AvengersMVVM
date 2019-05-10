package com.funrisestudio.avengers.domain

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import kotlin.coroutines.EmptyCoroutineContext

abstract class UseCase<out Type, in Params> (private val executor: Executor) where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, coroutineScope: CoroutineScope = CoroutineScope(EmptyCoroutineContext), then: (Either<Failure, Type>) -> Unit = {}) {
        coroutineScope.launch {
            val res = withContext(Dispatchers.IO) {
                run(params)
            }
            then.invoke(res)
        }
    }

    class None
}
