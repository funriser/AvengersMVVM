package com.funrisestudio.avengers.data

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.data.mapper.AvengerMapper
import com.funrisestudio.avengers.data.source.Firestore
import com.funrisestudio.avengers.domain.AvengersRepository
import com.funrisestudio.avengers.domain.entity.Avenger
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import java.util.concurrent.ExecutionException

class AvengersRepositoryImpl (private val firestore: Firestore,
                              private val avengersMapper: AvengerMapper): AvengersRepository {

    override fun avengers(): Either<Failure, List<Avenger>> =
            firebaseRequest(firestore.getAvengers(), avengersMapper::transform)

    private fun <T> firebaseRequest (task: Task<QuerySnapshot>, transform: (List<DocumentSnapshot>) -> T): Either<Failure, T> {
        return try {
            val response = Tasks.await(task)
            when (response.isEmpty) {
                false -> Either.Right(transform(response.documents))
                true -> Either.Left(Failure.ServerError())
            }
        } catch (e: ExecutionException) {
            e.printStackTrace()
            Either.Left(Failure.ServerError())
        } catch (e: InterruptedException) {
            e.printStackTrace()
            Either.Left(Failure.ServerError())
        }
    }

}