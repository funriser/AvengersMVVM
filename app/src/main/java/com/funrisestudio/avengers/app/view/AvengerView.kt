package com.funrisestudio.avengers.app.view

import android.os.Parcel
import android.os.Parcelable
import com.funrisestudio.avengers.domain.entity.Avenger
import java.text.SimpleDateFormat
import java.util.*

class AvengerView (val id: String, val name: String, val age: Int, val alias: String, val image: String, val dob: String, val story: String) : Parcelable {

    constructor (avenger: Avenger): this (
            avenger.id,
            avenger.name,
            avenger.age,
            avenger.alias,
            avenger.image,
            SimpleDateFormat("d MMM yyyy", Locale.ENGLISH).format(avenger.dob),
            avenger.story
    )

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeString(alias)
        parcel.writeString(image)
        parcel.writeString(dob)
        parcel.writeString(story)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AvengerView> {
        override fun createFromParcel(parcel: Parcel): AvengerView {
            return AvengerView(parcel)
        }

        override fun newArray(size: Int): Array<AvengerView?> {
            return arrayOfNulls(size)
        }
    }

}