package com.funrisestudio.avengers.data.source

import com.funrisestudio.avengers.domain.entity.Avenger
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class Firestore (private val db: FirebaseFirestore) {

    companion object {
        private const val PATH_AVENGERS = "avengers"
    }

    fun getAvengers (): Task<List<Avenger>> =
            db.collection(PATH_AVENGERS).get().continueWith {
                t -> t.result.mapMultiple(Avenger::class.java) { apply { id = it.id } }
            }

    private fun <T> QuerySnapshot.mapMultiple(targetClass: Class<T>, apply: T.(DocumentSnapshot) -> T): List<T> =
            documents.map { it.toObject(targetClass)!!.apply(it) }

}