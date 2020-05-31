package com.funrisestudio.avengers.data.source

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.core.extensions.execute
import com.funrisestudio.avengers.data.AvengersRemoteSource
import com.funrisestudio.avengers.domain.entity.Avenger
import com.funrisestudio.avengers.domain.entity.AvengerMovie
import com.google.firebase.firestore.FirebaseFirestore

class AppFirestore(
    private val db: FirebaseFirestore,
    private val firestoreQuery: FirestoreQuery
): AvengersRemoteSource {

    companion object {
        private const val COLLECTION_AVENGERS = "avengers"
        private const val COLLECTION_MOVIES = "movies"
    }

    override suspend fun getAvengers(): Either<Failure, List<Avenger>> {
        val avengersCollection = db.collection(COLLECTION_AVENGERS)
        val query = firestoreQuery.getAndMapIds<Avenger>(avengersCollection) { id, entity ->
            entity?.id = id
            entity
        }
        return query.execute {
            it.filterNotNull()
        }
    }

    override suspend fun getAvengerMovies(avengerId: String): Either<Failure, List<AvengerMovie>> {
        val moviesReference = "$COLLECTION_AVENGERS/$avengerId/$COLLECTION_MOVIES"
        val query = firestoreQuery.get<AvengerMovie>(db.collection(moviesReference))
        return query.execute {
            it.filterNotNull()
        }
    }

}