package com.funrisestudio.avengers.domain

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.google.android.gms.tasks.Tasks
import java.util.concurrent.Callable
import java.util.concurrent.Executors

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        Tasks.call(Executors.newSingleThreadExecutor(), Callable { run(params) })
                .addOnCompleteListener { onResult.invoke(it.result) }
        /*val job = async(CommonPool) { run(params) }
        launch(UI) { onResult(job.await()) }*/
    }

    class None
}