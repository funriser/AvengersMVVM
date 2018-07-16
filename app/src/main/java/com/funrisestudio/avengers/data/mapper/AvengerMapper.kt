package com.funrisestudio.avengers.data.mapper

import com.funrisestudio.avengers.domain.entity.Avenger
import com.google.firebase.firestore.DocumentSnapshot

class AvengerMapper {

    fun transform (avengers: List<DocumentSnapshot>): List<Avenger> = avengers.map { transform (it) }

    fun transform (avenger: DocumentSnapshot): Avenger = Avenger(
            name = avenger["name"] as String,
            age = avenger["age"] as Int,
            alias = avenger["alias"] as String
    )

}