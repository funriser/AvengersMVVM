package com.funrisestudio.avengers.app.avengerDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.funrisestudio.avengers.R
import com.funrisestudio.avengers.app.view.AvengerMovieView
import com.funrisestudio.avengers.app.view.AvengerView
import com.funrisestudio.avengers.core.base.BaseActivity
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.core.extensions.*

import kotlinx.android.synthetic.main.activity_avenger_detail.*
import kotlinx.android.synthetic.main.content_avenger_detail.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AvengerDetailActivity : BaseActivity(), AvengerDetailsAnimator.AppBarSlideListener {

    private val movieAdapter: AvengerMoviesAdapter by inject()
    private val activityAnimator: AvengerDetailsAnimator by inject()
    private val viewModel: AvengerDetailViewModel by viewModel()

    private lateinit var avengerId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avenger_detail)
        setSupportActionBar(toolbar)

        activityAnimator.disableOverlayForTransition(this)
        activityAnimator.postponeTransition(this)

        with(viewModel) {
            observe(avengerMovies, ::renderAvengerMovies)
            failure(failure, ::handleFailure)
        }

        val args = intent.getParcelableExtra(AVENGER_DETAIL) as AvengerView
        avengerId = args.id

        initView()
        loadAvengerMovies()
        showAvengerDetails(args)
    }

    private fun loadAvengerMovies() {
        showProgress()
        viewModel.getAvengerMovies(avengerId)
    }

    private fun initView() {
        activityAnimator.setAppBarSlideListener(appBarDetail, this, 20)
        rvAvengerMovies.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        rvAvengerMovies.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
        rvAvengerMovies.adapter = movieAdapter
    }

    fun showAvengerDetails(avengerView: AvengerView) {
        collapsingToolbar.title = avengerView.alias
        tvDetailAge.text = avengerView.age.toString()
        tvDetailDoB.text = avengerView.dob
        tvDetailStory.text = avengerView.story
        ivAvenger.loadUrlAndStartTransition(avengerView.image, this)
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
        activityAnimator.scaleDownView(fab)
    }

    override fun onAppBarClosed() {
        activityAnimator.scaleUpView(fab)
    }

    override fun onBackPressed() {
        activityAnimator.enableOverlayForTransition(this)
        super.onBackPressed()
    }

    override fun onDestroy() {
        activityAnimator.removeAppBarSlideListener(appBarDetail)
        super.onDestroy()
    }

    companion object {

        private const val AVENGER_DETAIL = "avengerDetail"

        fun callingIntent(context: Context, avengerView: AvengerView): Intent =
                Intent(context, AvengerDetailActivity::class.java).apply {
                    putExtra(AVENGER_DETAIL, avengerView)
                }

    }

}
