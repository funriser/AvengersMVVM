package com.funrisestudio.avengers.data

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.domain.AvengersRepository
import com.funrisestudio.avengers.domain.entity.Avenger
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class AvengersRepositoryImpl (private val firestore: FirebaseFirestore): AvengersRepository {

    companion object {

        private const val PATH_AVENGERS = "avengers"

    }

    override fun avengers(): Either<Failure, List<Avenger>> {
        firestore.collection(PATH_AVENGERS).get()
        TODO()
    }

    private fun <T, R> firebaseRequest (task: Task<QuerySnapshot>, transform: (T) -> R, default: T): Either<Failure, R> {
        TODO()
    }

}