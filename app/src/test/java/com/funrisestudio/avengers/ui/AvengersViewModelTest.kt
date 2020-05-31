package com.funrisestudio.avengers.ui

import com.funrisestudio.avengers.app.avengers.AvengersViewModel
import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.data.TestData
import com.funrisestudio.avengers.domain.interactor.GetAvengers
import com.funrisestudio.avengers.awaitValue
import com.funrisestudio.avengers.core.exception.Failure
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class AvengersViewModelTest: ViewModelTest() {

    private val interactor: GetAvengers = mock()
    private lateinit var viewModel: AvengersViewModel

    @Before
    fun setUp() {
        given { interactor.dispatcher }.willReturn(Dispatchers.Main)
    }

    @Test
    fun `loading avengers should update live data`() {
        val avengersList = TestData.getMockedAvengers()
        val avengersAnswer = Either.Right(avengersList)
        given { runBlocking { interactor.run(any()) } }.willReturn(avengersAnswer)

        //loading should run on view model initialization
        viewModel = AvengersViewModel(interactor)

        val actualProgressBeforeLoading = viewModel.progress.value
        val actualAvengersAfterLoading = viewModel.avengers.awaitValue()
        val actualFailureAfterLoading = viewModel.failure.awaitValue(timeMillis = 0)
        val actualProgressAfterLoading = viewModel.progress.awaitValue()

        assertEquals(true, actualProgressBeforeLoading)
        assertEquals(TestData.getMockedAvengerViews(), actualAvengersAfterLoading)
        assertNull(actualFailureAfterLoading)
        assertEquals(false, actualProgressAfterLoading)
    }

    @Test
    fun `loading avengers with failure should update live data`() {
        val avengersAnswer = Either.Left(Failure.NetworkConnection)
        given { runBlocking { interactor.run(any()) } }.willReturn(avengersAnswer)

        //loading should run on view model initialization
        viewModel = AvengersViewModel(interactor)

        val actualProgressBeforeLoading = viewModel.progress.value
        val actualFailureAfterLoading = viewModel.failure.awaitValue()
        val actualAvengersAfterLoading = viewModel.avengers.awaitValue(timeMillis = 0)
        val actualProgressAfterLoading = viewModel.progress.awaitValue()

        assertEquals(true, actualProgressBeforeLoading)
        assertEquals(Failure.NetworkConnection, actualFailureAfterLoading)
        assertNull(actualAvengersAfterLoading)
        assertEquals(false, actualProgressAfterLoading)
    }

}