package com.funrisestudio.avengers.data

import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.NetworkHandler
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.data.source.Firestore
import com.funrisestudio.avengers.domain.entity.Avenger
import com.funrisestudio.avengers.domain.entity.AvengerMovie
import com.google.android.gms.common.util.ThreadUtils
import com.google.android.gms.tasks.Tasks
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import java.util.*

@RunWith(PowerMockRunner::class)
@PrepareForTest(ThreadUtils::class)
class AvengersRepositoryTest {

    private lateinit var repository: AvengersRepositoryImpl
    @Mock private lateinit var firestore: Firestore
    @Mock private lateinit var networkHandler: NetworkHandler

    private val testDate = Date()

    @Before
    fun setUp () {
        PowerMockito.mockStatic(ThreadUtils::class.java)
        MockitoAnnotations.initMocks(this)
        repository = AvengersRepositoryImpl(firestore, networkHandler)
    }

    @Test
    fun `Getting avengers from network` () {

        whenever(networkHandler.isConnected).thenReturn(true)
        whenever(firestore.getAvengers()).thenReturn(Tasks.forResult(mockAvengers()))
        whenever(ThreadUtils.isMainThread()).thenReturn(false)

        val avengersResult = repository.avengers()
        assertEquals(avengersResult, Either.Right(mockAvengers()))
        verify(firestore).getAvengers()
    }

    @Test
    fun `Getting avengers from network error` () {
        whenever(networkHandler.isConnected).thenReturn(false)
        whenever(firestore.getAvengers()).thenReturn(Tasks.forResult(mockAvengers()))
        whenever(ThreadUtils.isMainThread()).thenReturn(false)

        val avengersResult = repository.avengers()
        assertTrue(avengersResult.isLeft)
        assertTrue((avengersResult as Either.Left).a is Failure.NetworkConnection)
        verifyNoMoreInteractions(firestore)
    }


    @Test
    fun `Getting avenger movies from network` () {

        val avengerId = "Av1"

        whenever(networkHandler.isConnected).thenReturn(true)
        whenever(firestore.getAvengerMovies(avengerId)).thenReturn(Tasks.forResult(mockAvengerMovies()))
        whenever(ThreadUtils.isMainThread()).thenReturn(false)

        val avengersResult = repository.avengerMovies(avengerId)
        assertEquals(avengersResult, Either.Right(mockAvengerMovies()))
        verify(firestore).getAvengerMovies(avengerId)
    }

    @Test
    fun `Getting avenger movies from network error` () {

        val avengerId = "Av1"

        whenever(networkHandler.isConnected).thenReturn(false)
        whenever(firestore.getAvengerMovies(avengerId)).thenReturn(Tasks.forResult(mockAvengerMovies()))
        whenever(ThreadUtils.isMainThread()).thenReturn(false)

        val avengersResult = repository.avengerMovies(avengerId)
        assertTrue(avengersResult.isLeft)
        assertTrue((avengersResult as Either.Left).a is Failure.NetworkConnection)
        verifyNoMoreInteractions(firestore)
    }

    private fun mockAvengers () =
            listOf(
                    Avenger("1", "Tony", 50,
                            "Iron Man", "", testDate, "Cool Story"),
                    Avenger("2", "Cap", 50,
                            "Captain America", "", testDate, "Cool Story")
            )

    private fun mockAvengerMovies () =
            listOf(
                    AvengerMovie("Movie1", ""),
                    AvengerMovie("Movie2", ""),
                    AvengerMovie("Movie3", "")
            )
}