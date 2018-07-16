package com.funrisestudio.avengers.domain

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.domain.entity.Avenger

interface AvengersRepository {

    fun avengers (): Either<Failure, List<Avenger>>

}