package com.funrisestudio.avengers.app.view

import android.os.Parcelable
import com.funrisestudio.avengers.domain.entity.AvengerMovie
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AvengerMovieView(val name: String, val poster: String) : Parcelable {
    constructor (avengerMovie: AvengerMovie) : this(avengerMovie.name, avengerMovie.poster)
}