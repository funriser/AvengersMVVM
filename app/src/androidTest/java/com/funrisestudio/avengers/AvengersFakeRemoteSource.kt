package com.funrisestudio.avengers

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.data.AvengersRemoteSource
import com.funrisestudio.avengers.domain.entity.Avenger
import com.funrisestudio.avengers.domain.entity.AvengerMovie

class AvengersFakeRemoteSource: AvengersRemoteSource {

    override suspend fun getAvengers(): Either<Failure, List<Avenger>> {
        return onGetAvengers()
    }

    override suspend fun getAvengerMovies(avengerId: String): Either<Failure, List<AvengerMovie>> {
        return onGetAvengerMovies()
    }

    companion object {

        var onGetAvengers: suspend () -> Either<Failure, List<Avenger>> = {
            Either.Right(TestData.avengers)
        }

        var onGetAvengerMovies: suspend () -> Either<Failure, List<AvengerMovie>> = {
            Either.Right(TestData.movies)
        }

    }

}