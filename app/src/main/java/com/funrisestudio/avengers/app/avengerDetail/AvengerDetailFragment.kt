package com.funrisestudio.avengers.app.avengerDetail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.funrisestudio.avengers.R
import com.funrisestudio.avengers.app.view.AvengerMovieView
import com.funrisestudio.avengers.core.base.BaseFragment
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.core.extensions.failure
import com.funrisestudio.avengers.core.extensions.loadUrlAndStartTransition
import com.funrisestudio.avengers.core.extensions.observe
import com.funrisestudio.avengers.core.extensions.popSnackbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_avenger_detail.*
import kotlinx.android.synthetic.main.content_avenger_detail.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AvengerDetailFragment : BaseFragment(), AvengerDetailsAnimator.AppBarSlideListener {

    private val movieAdapter: AvengerMoviesAdapter by inject()
    private val fragmentAnimator: AvengerDetailsAnimator by inject()
    private val viewModel: AvengerDetailViewModel by viewModel()
    private val args: AvengerDetailFragmentArgs by navArgs()

    override var layoutId: Int = R.layout.fragment_avenger_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentAnimator.run {
            setUpTransitions(this@AvengerDetailFragment)
            postponeTransition(this@AvengerDetailFragment)
        }
        with(viewModel) {
            observe(avengerMovies, ::renderAvengerMovies)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        loadAvengerMovies()
        showAvengerDetails()
    }

    private fun loadAvengerMovies() {
        showProgress()
        viewModel.getAvengerMovies(args.avenger.id)
    }

    private fun initView() {
        ivAvenger.transitionName = args.transitionName
        fragmentAnimator.setAppBarSlideListener(appBarDetail, this, 20)
        rvAvengerMovies.itemAnimator = DefaultItemAnimator()
        rvAvengerMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvAvengerMovies.adapter = movieAdapter
    }


    private fun showAvengerDetails() {
        args.avenger.let {
            collapsingToolbar.title = it.alias
            tvDetailAge.text = it.age.toString()
            tvDetailDoB.text = it.dob
            tvDetailStory.text = it.story
            ivAvenger.loadUrlAndStartTransition(it.image, this)
        }
    }

    private fun renderAvengerMovies(avengerMovies: List<AvengerMovieView>?) {
        movieAdapter.listMovies = avengerMovies.orEmpty()
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        hideProgress()
        when (failure) {
            is Failure.NetworkConnection ->
                popSnackbar(layoutDetail,
                        getString(R.string.error_network), Snackbar.LENGTH_INDEFINITE,
                        getString(R.string.error_try_again)) { loadAvengerMovies() }
            else -> popSnackbar(layoutDetail, getString(R.string.error_server))
        }
    }

    override fun onAppBarOpened() {
        fragmentAnimator.scaleDownView(fab)
    }

    override fun onAppBarClosed() {
        fragmentAnimator.scaleUpView(fab)
    }

}