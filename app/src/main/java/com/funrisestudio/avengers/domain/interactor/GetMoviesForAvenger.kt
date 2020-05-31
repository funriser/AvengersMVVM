package com.funrisestudio.avengers.domain.interactor

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.domain.AvengersRepository
import com.funrisestudio.avengers.domain.UseCase
import com.funrisestudio.avengers.domain.entity.AvengerMovie

open class GetMoviesForAvenger(
    private val avengersRepository: AvengersRepository
) : UseCase<List<AvengerMovie>, String>() {

    override suspend fun run(params: String): Either<Failure, List<AvengerMovie>> {
        return avengersRepository.avengerMovies(params)
    }

}