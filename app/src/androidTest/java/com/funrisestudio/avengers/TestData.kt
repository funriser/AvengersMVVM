package com.funrisestudio.avengers

import com.funrisestudio.avengers.domain.entity.Avenger
import com.funrisestudio.avengers.domain.entity.AvengerMovie
import java.util.*

object TestData {

    val avengers = listOf(
            Avenger(
                    id = "1",
                    name = "Tony",
                    age = 52,
                    alias = "Iron man",
                    image = "",
                    dob = Date(),
                    story = "Some story"
            )
    )

    val movies = listOf(
            AvengerMovie(
                    name = "Movie name",
                    poster = ""
            )
    )

}