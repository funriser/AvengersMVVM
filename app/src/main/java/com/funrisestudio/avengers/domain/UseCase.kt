package com.funrisestudio.avengers.domain

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.google.android.gms.tasks.Tasks
import java.util.concurrent.Callable
import java.util.concurrent.Executor

abstract class UseCase<out Type, in Params> (private val executor: Executor) where Type : Any {

    abstract fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        Tasks
                .call(executor, Callable { run(params) })
                .addOnCompleteListener { onResult.invoke(it.result) }
    }

    class None
}