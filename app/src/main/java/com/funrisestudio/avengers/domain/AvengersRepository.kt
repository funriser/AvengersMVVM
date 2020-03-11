package com.funrisestudio.avengers.domain

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.domain.entity.Avenger
import com.funrisestudio.avengers.domain.entity.AvengerMovie

interface AvengersRepository {

    suspend fun avengers(): Either<Failure, List<Avenger>>

    suspend fun avengerMovies(avengerId: String): Either<Failure, List<AvengerMovie>>

}