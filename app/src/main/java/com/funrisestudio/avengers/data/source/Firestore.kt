package com.funrisestudio.avengers.data.source

import com.funrisestudio.avengers.domain.entity.Avenger
import com.funrisestudio.avengers.domain.entity.AvengerMovie
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class Firestore (private val db: FirebaseFirestore) {

    companion object {
        private const val PATH_AVENGERS = "avengers"
        private const val COLLECTION_MOVIES = "movies"
    }

    fun getAvengers (): Task<List<Avenger>> =
            db.collection(PATH_AVENGERS).get().continueWith {
                t -> t.result?.mapMultiple(Avenger::class.java) { apply { id = it.id } }
            }

    fun getAvengerMovies (avengerId: String): Task<List<AvengerMovie>> =
            db.collection("$PATH_AVENGERS/$avengerId/$COLLECTION_MOVIES").get().continueWith {
                it.result?.mapMultiple(AvengerMovie::class.java)
            }

    private fun <T> QuerySnapshot.mapMultiple(targetClass: Class<T>, apply: T.(DocumentSnapshot) -> T = { this }): List<T> =
            documents.map { it.toObject(targetClass)!!.apply(it) }

}