package com.funrisestudio.avengers.data

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.data.network.ConnectionState
import com.funrisestudio.avengers.domain.AvengersRepository
import com.funrisestudio.avengers.domain.entity.Avenger
import com.funrisestudio.avengers.domain.entity.AvengerMovie

class AvengersRepositoryImpl(
    private val avengersRemoteSource: AvengersRemoteSource,
    private val connectionState: ConnectionState
) : AvengersRepository {

    override suspend fun avengers(): Either<Failure, List<Avenger>> {
        return if (!connectionState.isConnected()) {
            Either.Left(Failure.NetworkConnection)
        } else {
            avengersRemoteSource.getAvengers()
        }
    }

    override suspend fun avengerMovies(avengerId: String): Either<Failure, List<AvengerMovie>> {
        return if (!connectionState.isConnected()) {
            Either.Left(Failure.NetworkConnection)
        } else {
            avengersRemoteSource.getAvengerMovies(avengerId)
        }
    }

}