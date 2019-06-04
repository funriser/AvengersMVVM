package com.funrisestudio.avengers.data

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.NetworkHandler
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.core.extensions.await
import com.funrisestudio.avengers.data.source.Firestore
import com.funrisestudio.avengers.domain.AvengersRepository
import com.funrisestudio.avengers.domain.entity.Avenger
import com.funrisestudio.avengers.domain.entity.AvengerMovie
import com.google.android.gms.tasks.Task
import java.util.concurrent.ExecutionException

class AvengersRepositoryImpl(
        private val firestore: Firestore,
        private val networkHandler: NetworkHandler
) : AvengersRepository {

    override suspend fun avengers(): Either<Failure, List<Avenger>> {
        return if (!networkHandler.isConnected)
            Either.Left(Failure.NetworkConnection())
        else
            firebaseRequest(firestore.getAvengers()) { it }
    }

    override suspend fun avengerMovies(avengerId: String): Either<Failure, List<AvengerMovie>> {
        return if (!networkHandler.isConnected)
            Either.Left(Failure.NetworkConnection())
        else
            firebaseRequest(firestore.getAvengerMovies(avengerId)) { it }
    }

    private suspend fun <T, R> firebaseRequest(task: Task<T>, transform: (T) -> R): Either<Failure, R> {
        return try {
            val response = task.await()
            if (response != null && task.isSuccessful && task.isComplete)
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