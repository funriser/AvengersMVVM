package com.funrisestudio.avengers.domain

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.domain.entity.AvengerMovie
import com.funrisestudio.avengers.domain.interactor.GetMoviesForAvenger
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executors

class GetMoviesForAvengerTest {

    @Mock
    private lateinit var repository: AvengersRepository

    private lateinit var interactor: GetMoviesForAvenger

    @Before
    fun setUp () {
        MockitoAnnotations.initMocks(this)
        interactor = GetMoviesForAvenger(repository, Executors.newCachedThreadPool())
    }

    @Test
    fun `Get avengers success` () {
        val avengerId = "Av1"
        whenever(repository.avengerMovies(avengerId)).thenReturn(
                Either.Right(mockAvengerMovies())
        )
        val result = interactor.run(avengerId)
        Assert.assertEquals(result.isRight, true)
        Assert.assertEquals((result as Either.Right).b, mockAvengerMovies())
        verify(repository).avengerMovies(avengerId)
        verifyNoMoreInteractions(repository)
    }

    private fun mockAvengerMovies () =
            listOf(
                    AvengerMovie("Movie1", ""),
                    AvengerMovie("Movie2", ""),
                    AvengerMovie("Movie3", "")
            )

}