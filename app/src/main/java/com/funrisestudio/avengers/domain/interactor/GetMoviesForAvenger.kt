package com.funrisestudio.avengers.domain.interactor

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.domain.AvengersRepository
import com.funrisestudio.avengers.domain.UseCase
import com.funrisestudio.avengers.domain.entity.AvengerMovie
import java.util.concurrent.Executor

class GetMoviesForAvenger(private val avengersRepository: AvengersRepository, executor: Executor)
    : UseCase<List<AvengerMovie>, String>(executor) {

    override suspend fun run(params: String): Either<Failure, List<AvengerMovie>> =
            avengersRepository.avengerMovies(params)

}