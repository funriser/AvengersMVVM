package com.funrisestudio.avengers.data

import com.funrisestudio.avengers.domain.entity.Avenger
import com.funrisestudio.avengers.domain.entity.AvengerMovie
import java.util.*

object TestData {

    fun getMockedAvengers(): List<Avenger> {
        return listOf(
                Avenger(
                        id = "1",
                        name = "Tony Stark",
                        age = 42,
                        alias = "Iron Man",
                        image = "https://image",
                        dob = Date(),
                        story = "Cool story"
                ),
                Avenger(
                        id = "2",
                        name = "Steve Rogers",
                        age = 42,
                        alias = "Captain America",
                        image = "https://image",
                        dob = Date(),
                        story = "Cool story"
                ),
                Avenger(
                        id = "3",
                        name = "Scarlett Johansson",
                        age = 24,
                        alias = "Black widow",
                        image = "https://image",
                        dob = Date(),
                        story = "Cool story"
                )
        )
    }

    fun getMockedAvengerMovies(): List<AvengerMovie> {
        return listOf(
                AvengerMovie(
                        name = "Iron Man 1",
                        poster = "https://image"
                ),
                AvengerMovie(
                        name = "Iron Man 2",
                        poster = "https://image"
                ),
                AvengerMovie(
                        name = "Iron Man 3",
                        poster = "https://image"
                )
        )
    }

}