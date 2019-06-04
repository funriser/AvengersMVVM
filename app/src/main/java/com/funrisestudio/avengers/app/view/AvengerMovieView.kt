package com.funrisestudio.avengers.app.view

import android.os.Parcel
import android.os.Parcelable
import com.funrisestudio.avengers.domain.entity.AvengerMovie

class AvengerMovieView(val name: String, val poster: String) : Parcelable {

    constructor (avengerMovie: AvengerMovie) : this(avengerMovie.name, avengerMovie.poster)

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(poster)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AvengerMovieView> {
        override fun createFromParcel(parcel: Parcel): AvengerMovieView {
            return AvengerMovieView(parcel)
        }

        override fun newArray(size: Int): Array<AvengerMovieView?> {
            return arrayOfNulls(size)
        }
    }
}