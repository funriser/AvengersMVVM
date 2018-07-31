package com.funrisestudio.avengers.domain.entity

data class Avenger (var name: String, var age: Int, var alias: String, var image: String) {

    constructor(): this ("", 0, "", "")

}