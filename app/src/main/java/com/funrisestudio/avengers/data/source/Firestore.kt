package com.funrisestudio.avengers.data.source

import com.funrisestudio.avengers.domain.entity.Avenger
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore

class Firestore (private val db: FirebaseFirestore) {

    companion object {
        private const val PATH_AVENGERS = "avengers"
    }

    fun getAvengers (): Task<List<Avenger>> =
            db.collection(PATH_AVENGERS).get().continueWith { it.result.toObjects(Avenger::class.java) }

}