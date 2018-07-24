package com.funrisestudio.avengers.domain

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.domain.entity.Avenger
import com.funrisestudio.avengers.domain.interactor.GetAvengers
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import io.kotlintest.mock.mock
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Before
import org.junit.Test

class GetAvengersTest {

    private lateinit var repository: AvengersRepository

    private lateinit var interactor: GetAvengers

    @Before
    fun setUp () {
        repository = mock()
        interactor = GetAvengers(repository)
    }

    @Test
    fun `Get avengers success` () {
        whenever(repository.avengers()).thenReturn(
                Either.Right(listOf(Avenger("Tony", 50, "Iron Man")))
        )
        val result = runBlocking { interactor.run(UseCase.None()) }
        assertEquals (result.isRight, true)
        assertEquals((result as Either.Right).b, listOf(Avenger("Tony", 50, "Iron Man")))
        verify(repository).avengers()
        verifyNoMoreInteractions(repository)
    }


}