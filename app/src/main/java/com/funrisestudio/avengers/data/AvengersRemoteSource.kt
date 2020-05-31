package com.funrisestudio.avengers.data

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.domain.entity.Avenger
import com.funrisestudio.avengers.domain.entity.AvengerMovie

interface AvengersRemoteSource {

    suspend fun getAvengers(): Either<Failure, List<Avenger>>

    suspend fun getAvengerMovies(avengerId: String): Either<Failure, List<AvengerMovie>>

}