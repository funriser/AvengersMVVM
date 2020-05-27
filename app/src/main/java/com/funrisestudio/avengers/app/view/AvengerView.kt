package com.funrisestudio.avengers.app.view

import android.os.Parcelable
import com.funrisestudio.avengers.domain.entity.Avenger
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class AvengerView(
    val id: String, val name: String, val age: Int, val alias: String,
    val image: String, val dob: String, val story: String
) : Parcelable {

    constructor (avenger: Avenger) : this(
            avenger.id,
            avenger.name,
            avenger.age,
            avenger.alias,
            avenger.image,
            SimpleDateFormat("d MMM yyyy", Locale.ENGLISH).format(avenger.dob),
            avenger.story
    )

}