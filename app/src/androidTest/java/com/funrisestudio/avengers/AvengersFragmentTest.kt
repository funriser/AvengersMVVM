package com.funrisestudio.avengers

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.funrisestudio.avengers.app.avengers.AvengersAdapter
import com.funrisestudio.avengers.app.avengers.AvengersFragment
import com.funrisestudio.avengers.core.Either
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.data.AvengersRemoteSource
import kotlinx.coroutines.delay
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class AvengersFragmentTest {

    @Before
    fun setUp() {
        loadKoinModules(
                module {
                    single<AvengersRemoteSource>(override = true) { AvengersFakeRemoteSource() }
                }
        )
    }

    @Test
    fun should_start_with_loader() {
        //Arrange
        AvengersFakeRemoteSource.onGetAvengers = {
            delay(Long.MAX_VALUE)
            Either.Right(TestData.avengers)
        }
        //Act
        launchFragmentInContainer<AvengersFragment>(
                themeResId = R.style.AppTheme
        )
        //Assert
        onView(withId(R.id.progressSpinner)).check(matches(isDisplayed()))
    }

    @Test
    fun should_show_avengers_list() {
        //Arrange
        AvengersFakeRemoteSource.onGetAvengers = {
            Either.Right(TestData.avengers)
        }
        //Act
        launchFragmentInContainer<AvengersFragment>(
                themeResId = R.style.AppTheme
        )
        //Assert
        onView(withId(R.id.rvAvengers)).check(matches(isDisplayed()))
        onView(withText("Tony")).check(matches(isDisplayed()))
        onView(withId(R.id.progressSpinner)).check(matches(not(isDisplayed())))
    }

    @Test
    fun should_show_error_if_failed_to_load() {
        //Arrange
        AvengersFakeRemoteSource.onGetAvengers = {
            Either.Left(Failure.NetworkConnection)
        }

        //Act
        var fragment: Fragment? = null
        launchFragmentInContainer<AvengersFragment>(
                themeResId = R.style.AppTheme
        ).onFragment {
            fragment = it
        }

        //Assert
        onView(withId(R.id.progressSpinner)).check(matches(not(isDisplayed())))
        //check if error snackbar is displayed
        onView(withText(R.string.error_network)).check(matches(isDisplayed()))
        //check mocked list data not displayed
        val rv = fragment!!.requireActivity().findViewById<RecyclerView>(R.id.rvAvengers)
        assertThat(rv.adapter!!.itemCount, `is`(0))
    }

    @Test
    fun should_open_details_screen_on_hero_click() {
        //Arrange
        AvengersFakeRemoteSource.onGetAvengers = {
            Either.Right(TestData.avengers)
        }
        val navController = TestNavHostController(
                ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.nav_graph)
        launchFragmentInContainer<AvengersFragment>(
                themeResId = R.style.AppTheme
        ).onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }
        //Assert
        onView(withId(R.id.rvAvengers))
                .perform(RecyclerViewActions.actionOnItemAtPosition<AvengersAdapter.ViewHolder>(0, click()))
        assertThat(navController.currentDestination?.id, `is`(R.id.avengerDetailFragment))
    }

}
