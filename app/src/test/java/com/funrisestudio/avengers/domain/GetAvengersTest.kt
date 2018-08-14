package com.funrisestudio.avengers.domain

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.domain.entity.Avenger
import com.funrisestudio.avengers.domain.interactor.GetAvengers
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*
import java.util.concurrent.Executors

class GetAvengersTest {

    @Mock private lateinit var repository: AvengersRepository

    private lateinit var interactor: GetAvengers

    private val testDate = Date()

    @Before
    fun setUp () {
        MockitoAnnotations.initMocks(this)
        interactor = GetAvengers(repository, Executors.newCachedThreadPool())
    }

    @Test
    fun `Get avengers success` () {
        whenever(repository.avengers()).thenReturn(
                Either.Right(mockAvengers())
        )
        val result = interactor.run(UseCase.None())
        assertEquals (result.isRight, true)
        assertEquals((result as Either.Right).b, mockAvengers())
        verify(repository).avengers()
        verifyNoMoreInteractions(repository)
    }

    private fun mockAvengers () =
            listOf(
                    Avenger("1", "Tony", 50,
                            "Iron Man", "", testDate, "Cool Story"),
                    Avenger("2", "Cap", 50,
                            "Captain America", "", testDate, "Cool Story")
            )

}