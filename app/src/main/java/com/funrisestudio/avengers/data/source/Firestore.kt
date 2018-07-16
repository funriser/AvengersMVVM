package com.funrisestudio.avengers.data.source

import com.google.firebase.firestore.FirebaseFirestore

class Firestore (private val db: FirebaseFirestore) {

    companion object {
        private const val PATH_AVENGERS = "avengers"
    }

    fun getAvengers () = db.collection(PATH_AVENGERS).get()

}