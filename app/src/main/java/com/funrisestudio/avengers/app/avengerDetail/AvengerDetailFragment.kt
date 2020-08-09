package com.funrisestudio.avengers.app.avengerDetail

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.funrisestudio.avengers.R
import com.funrisestudio.avengers.app.view.AvengerMovieView
import com.funrisestudio.avengers.app.view.AvengerView
import com.funrisestudio.avengers.core.base.BaseFragment
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.core.extensions.failure
import com.funrisestudio.avengers.core.extensions.loadUrlAndStartTransition
import com.funrisestudio.avengers.core.extensions.observe
import com.funrisestudio.avengers.core.extensions.popSnackbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_avenger_detail.*
import kotlinx.android.synthetic.main.content_avenger_detail.*
import kotlinx.android.synthetic.main.layout_avenger_details_header.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AvengerDetailFragment : BaseFragment(), AvengerDetailsAnimator.AppBarSlideListener {

    private val movieAdapter: AvengerMoviesAdapter by inject()
    private val fragmentAnimator: AvengerDetailsAnimator by inject()
    private val args: AvengerDetailFragmentArgs by navArgs()
    private val viewModel: AvengerDetailViewModel by viewModel { parametersOf(args.avenger) }

    override var layoutId: Int = R.layout.fragment_avenger_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentAnimator.run {
            setUpTransitions(this@AvengerDetailFragment)
            postponeTransition(this@AvengerDetailFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            observe(avengerMovies, ::renderAvengerMovies)
            observe(avengerDetails, ::renderAvengerDetails)
            observe(progress, ::renderProgress)
            failure(failure, ::handleFailure)
        }
        initView()
    }

    private fun initView() {
        ivAvenger.transitionName = args.transitionName
        fragmentAnimator.setAppBarSlideListener(appBarDetail, this, 20)
        rvAvengerMovies.itemAnimator = DefaultItemAnimator()
        rvAvengerMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvAvengerMovies.adapter = movieAdapter
    }

    private fun renderAvengerDetails(avengerView: AvengerView?) {
        avengerView?.let {
            tvAvengerHeader.text = it.alias
            tvDetailAge.text = it.age.toString()
            tvDetailDoB.text = it.dob
            tvDetailStory.text = it.story
            ivAvenger.loadUrlAndStartTransition(it.image, this)
        }
    }

    private fun renderAvengerMovies(avengerMovies: List<AvengerMovieView>?) {
        movieAdapter.listMovies = avengerMovies.orEmpty()
    }

    private fun renderProgress(isShown: Boolean?) {
        if (isShown == true) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection ->
                popSnackbar(getString(R.string.error_network), Snackbar.LENGTH_INDEFINITE,
                        getString(R.string.error_try_again)) { viewModel.loadAvengerMovies() }
            else -> popSnackbar( getString(R.string.error_server))
        }
    }

    override fun onAppBarOpened() {
        fragmentAnimator.scaleDownView(fab)
    }

    override fun onAppBarClosed() {
        fragmentAnimator.scaleUpView(fab)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentAnimator.removeAppBarSlideListener(appBarDetail)
    }

}