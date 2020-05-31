package com.funrisestudio.avengers.ui

import com.funrisestudio.avengers.app.avengerDetail.AvengerDetailViewModel
import com.funrisestudio.avengers.app.view.AvengerView
import com.funrisestudio.avengers.awaitValue
import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.data.TestData
import com.funrisestudio.avengers.domain.interactor.GetMoviesForAvenger
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class AvengerDetailViewModelTest: ViewModelTest() {

    private val interactor: GetMoviesForAvenger = mock()
    private lateinit var viewModel: AvengerDetailViewModel

    @Before
    fun setUp() {
        given { interactor.dispatcher }.willReturn(Dispatchers.Main)
    }

    @Test
    fun `avenger details live data should be populated from initial param`() {
        val avenger: AvengerView = TestData.getMockedAvengerView()
        viewModel = AvengerDetailViewModel(interactor, avenger)

        val actualDetails = viewModel.avengerDetails.awaitValue()

        assertEquals(avenger, actualDetails)
    }

    @Test
    fun `should load movies on initialization and populate live data`() {
        val avenger: AvengerView = TestData.getMockedAvengerView()
        val moviesList = TestData.getMockedAvengerMovies()
        val moviesAnswer = Either.Right(moviesList)
        given { runBlocking { interactor.run(any()) } }.willReturn(moviesAnswer)

        //should start loading on initialization
        viewModel = AvengerDetailViewModel(interactor, avenger)

        val actualProgressBeforeLoading = viewModel.progress.value
        val actualMoviesAfterLoading = viewModel.avengerMovies.awaitValue()
        val actualFailureAfterLoading = viewModel.failure.awaitValue(timeMillis = 0)
        val actualProgressAfterLoading = viewModel.progress.awaitValue()

        assertEquals(true, actualProgressBeforeLoading)
        assertEquals(TestData.getMockedAvengerMovieViews(), actualMoviesAfterLoading)
        assertNull(actualFailureAfterLoading)
        assertEquals(false, actualProgressAfterLoading)
    }

    @Test
    fun `loading movies with the failure should populate live data`() {
        val avenger: AvengerView = TestData.getMockedAvengerView()
        val moviesAnswer = Either.Left(Failure.NetworkConnection)
        given { runBlocking { interactor.run(any()) } }.willReturn(moviesAnswer)

        //should start loading on initialization
        viewModel = AvengerDetailViewModel(interactor, avenger)

        val actualProgressBeforeLoading = viewModel.progress.value
        val actualMoviesAfterLoading = viewModel.avengerMovies.awaitValue(timeMillis = 0)
        val actualFailureAfterLoading = viewModel.failure.awaitValue()
        val actualProgressAfterLoading = viewModel.progress.awaitValue()

        assertEquals(true, actualProgressBeforeLoading)
        assertNull(actualMoviesAfterLoading)
        assertEquals(Failure.NetworkConnection, actualFailureAfterLoading)
        assertEquals(false, actualProgressAfterLoading)
    }

}