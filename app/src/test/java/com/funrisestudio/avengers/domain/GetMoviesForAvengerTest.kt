package com.funrisestudio.avengers.domain

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.data.TestData
import com.funrisestudio.avengers.domain.interactor.GetMoviesForAvenger
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetMoviesForAvengerTest {

    private val avengersRepository: AvengersRepository = mock()
    private lateinit var interactor: GetMoviesForAvenger

    @Before
    fun setUp() {
        interactor = GetMoviesForAvenger(avengersRepository)
    }

    @Test
    fun `should get avenger movies list`(): Unit = runBlocking {
        val avengerId = "1"
        val expectedList = TestData.getMockedAvengerMovies()
        val expectedAnswer = Either.Right(expectedList)
        whenever(avengersRepository.avengerMovies(avengerId)).thenReturn(expectedAnswer)
        val actual = interactor.run(avengerId)
        assertEquals(expectedAnswer, actual)
        verify(avengersRepository).avengerMovies(avengerId)
        verifyNoMoreInteractions(avengersRepository)
    }

    @Test
    fun `should proceed with error from repository`(): Unit = runBlocking {
        val avengerId = "1"
        val expectedAnswer = Either.Left(Failure.NetworkConnection)
        whenever(avengersRepository.avengerMovies(avengerId)).thenReturn(expectedAnswer)
        val actual = interactor.run(avengerId)
        assertEquals(expectedAnswer, actual)
        verify(avengersRepository).avengerMovies(avengerId)
        verifyNoMoreInteractions(avengersRepository)
    }

}