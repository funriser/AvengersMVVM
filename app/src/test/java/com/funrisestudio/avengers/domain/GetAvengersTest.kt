package com.funrisestudio.avengers.domain

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.data.TestData
import com.funrisestudio.avengers.domain.interactor.GetAvengers
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAvengersTest {

    private val avengersRepository: AvengersRepository = mock()
    private lateinit var interactor: GetAvengers

    @Before
    fun setUp() {
        interactor = GetAvengers(avengersRepository)
    }

    @Test
    fun `should get avengers list`(): Unit = runBlocking {
        val expectedList = TestData.getMockedAvengers()
        val expectedAnswer = Either.Right(expectedList)
        whenever(avengersRepository.avengers()).thenReturn(expectedAnswer)
        val actual = interactor.run(UseCase.None())
        assertEquals(expectedAnswer, actual)
        verify(avengersRepository).avengers()
        verifyNoMoreInteractions(avengersRepository)
    }

    @Test
    fun `should proceed with error from repository`(): Unit = runBlocking {
        val expectedAnswer = Either.Left(Failure.NetworkConnection)
        whenever(avengersRepository.avengers()).thenReturn(expectedAnswer)
        val actual = interactor.run(UseCase.None())
        assertEquals(expectedAnswer, actual)
        verify(avengersRepository).avengers()
        verifyNoMoreInteractions(avengersRepository)
    }

}