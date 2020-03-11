package com.funrisestudio.avengers.domain.interactor

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.domain.AvengersRepository
import com.funrisestudio.avengers.domain.UseCase
import com.funrisestudio.avengers.domain.entity.Avenger

class GetAvengers(
    private val avengersRepository: AvengersRepository
) : UseCase<List<Avenger>, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, List<Avenger>> {
        return avengersRepository.avengers()
    }

}