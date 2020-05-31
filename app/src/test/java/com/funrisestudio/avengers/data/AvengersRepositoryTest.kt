package com.funrisestudio.avengers.data

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.data.network.ConnectionState
import com.funrisestudio.avengers.domain.AvengersRepository
import com.funrisestudio.avengers.domain.entity.Avenger
import com.funrisestudio.avengers.domain.entity.AvengerMovie
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AvengersRepositoryTest {

    private val avengersRemoteSource: AvengersRemoteSource = mock()
    private val connectionState: ConnectionState = mock()

    private lateinit var avengersRepository: AvengersRepository

    @Before
    fun setUp() {
        avengersRepository = AvengersRepositoryImpl(
                avengersRemoteSource, connectionState
        )
    }

    @Test
    fun `should get avengers list`(): Unit = runBlocking {
        val expectedList: List<Avenger> = TestData.getMockedAvengers()
        val expectedAnswer = Either.Right(expectedList)
        whenever(connectionState.isConnected()).thenReturn(true)
        whenever(avengersRemoteSource.getAvengers()).thenReturn(expectedAnswer)
        val actual = avengersRepository.avengers()
        assertEquals(expectedAnswer, actual)
        verify(connectionState).isConnected()
        verify(avengersRemoteSource).getAvengers()
        verifyNoMoreInteractions(connectionState)
        verifyNoMoreInteractions(avengersRemoteSource)
    }

    @Test
    fun `should not get avengers if network is not connected`(): Unit = runBlocking {
        val expectedAnswer = Either.Left(Failure.NetworkConnection)
        whenever(connectionState.isConnected()).thenReturn(false)
        val actual = avengersRepository.avengers()
        assertEquals(expectedAnswer, actual)
        verify(connectionState).isConnected()
        verifyNoMoreInteractions(connectionState)
        verifyZeroInteractions(avengersRemoteSource)
    }

    @Test
    fun `should proceed with error from remote source for avengers`(): Unit = runBlocking {
        val expectedAnswer = Either.Left(Failure.ServerError)
        whenever(connectionState.isConnected()).thenReturn(true)
        whenever(avengersRemoteSource.getAvengers()).thenReturn(expectedAnswer)
        val actual = avengersRepository.avengers()
        assertEquals(expectedAnswer, actual)
        verify(connectionState).isConnected()
        verify(avengersRemoteSource).getAvengers()
        verifyNoMoreInteractions(connectionState)
        verifyNoMoreInteractions(avengersRemoteSource)
    }

    @Test
    fun `should get avenger movies list`(): Unit = runBlocking {
        val avengerId = "1"
        val expectedList: List<AvengerMovie> = TestData.getMockedAvengerMovies()
        val expectedAnswer = Either.Right(expectedList)
        whenever(connectionState.isConnected()).thenReturn(true)
        whenever(avengersRemoteSource.getAvengerMovies(avengerId)).thenReturn(expectedAnswer)
        val actual = avengersRepository.avengerMovies(avengerId)
        assertEquals(expectedAnswer, actual)
        verify(connectionState).isConnected()
        verify(avengersRemoteSource).getAvengerMovies(avengerId)
        verifyNoMoreInteractions(connectionState)
        verifyNoMoreInteractions(avengersRemoteSource)
    }

    @Test
    fun `should not get avenger movies if network is not connected`(): Unit = runBlocking {
        val avengerId = "1"
        val expectedAnswer = Either.Left(Failure.NetworkConnection)
        whenever(connectionState.isConnected()).thenReturn(false)
        val actual = avengersRepository.avengerMovies(avengerId)
        assertEquals(expectedAnswer, actual)
        verify(connectionState).isConnected()
        verifyNoMoreInteractions(connectionState)
        verifyZeroInteractions(avengersRemoteSource)
    }

    @Test
    fun `should proceed with error from remote source for avenger movies`(): Unit = runBlocking {
        val avengerId = "1"
        val expectedAnswer = Either.Left(Failure.ServerError)
        whenever(connectionState.isConnected()).thenReturn(true)
        whenever(avengersRemoteSource.getAvengerMovies(avengerId)).thenReturn(expectedAnswer)
        val actual = avengersRepository.avengerMovies(avengerId)
        assertEquals(expectedAnswer, actual)
        verify(connectionState).isConnected()
        verify(avengersRemoteSource).getAvengerMovies(avengerId)
        verifyNoMoreInteractions(connectionState)
        verifyNoMoreInteractions(avengersRemoteSource)
    }

}