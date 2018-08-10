package com.funrisestudio.avengers.domain.entity

import java.util.*

data class Avenger (var id: String, var name: String, var age: Int, var alias: String, var image: String, var dob: Date, var story: String) {

    constructor(): this ("", "", 0, "", "", Date(), "")

}