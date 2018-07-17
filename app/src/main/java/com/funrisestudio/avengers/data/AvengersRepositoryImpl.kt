package com.funrisestudio.avengers.data

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.data.source.Firestore
import com.funrisestudio.avengers.domain.AvengersRepository
import com.funrisestudio.avengers.domain.entity.Avenger
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import java.util.concurrent.ExecutionException

class AvengersRepositoryImpl (private val firestore: Firestore): AvengersRepository {

    override fun avengers(): Either<Failure, List<Avenger>> =
            firebaseRequest(firestore.getAvengers()) { it }

    private fun <T, R> firebaseRequest (task: Task<T>, transform: (T) -> R): Either<Failure, R> {
        return try {
            val response = Tasks.await(task)
            if (response != null)
                Either.Right(transform(response))
            else
                Either.Left(Failure.ServerError())
        } catch (e: ExecutionException) {
            e.printStackTrace()
            Either.Left(Failure.ServerError())
        } catch (e: InterruptedException) {
            e.printStackTrace()
            Either.Left(Failure.ServerError())
        }
    }

}