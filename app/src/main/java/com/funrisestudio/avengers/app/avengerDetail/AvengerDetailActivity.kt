package com.funrisestudio.avengers.app.avengerDetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.view.ViewCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.funrisestudio.avengers.App
import com.funrisestudio.avengers.R
import com.funrisestudio.avengers.app.view.AvengerMovieView
import com.funrisestudio.avengers.app.view.AvengerView
import com.funrisestudio.avengers.core.base.BaseActivity
import com.funrisestudio.avengers.core.di.viewmodel.ViewModelFactory
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.core.extensions.*

import kotlinx.android.synthetic.main.activity_avenger_detail.*
import kotlinx.android.synthetic.main.content_avenger_detail.*
import javax.inject.Inject

class AvengerDetailActivity : BaseActivity (), AvengerDetailsAnimator.AppBarSlideListener {

    @Inject lateinit var viewModelFactory: ViewModelFactory

    @Inject lateinit var movieAdapter: AvengerMoviesAdapter

    @Inject lateinit var activityAnimator: AvengerDetailsAnimator

    private lateinit var viewModel: AvengerDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avenger_detail)
        setSupportActionBar(toolbar)
        App.appComponent.inject(this)

        activityAnimator.disableOverlayForTransition(this)
        activityAnimator.postponeTransition(this)

        viewModel = viewModel(viewModelFactory) {
            observe(avengerMovies, ::renderAvengerMovies)
            failure(failure, ::handleFailure)
        }

        val args = intent.getParcelableExtra(AVENGER_DETAIL) as AvengerView

        initView()
        loadAvengerMovies(args.id)
        showAvengerDetails(args)
    }

    private fun loadAvengerMovies (avengerId: String) {
        showProgress()
        viewModel.getAvengerMovies(avengerId)
    }

    private fun initView () {
        activityAnimator.setAppBarSlideListener(appBarDetail, this, 20)
        rvAvengerMovies.itemAnimator = DefaultItemAnimator ()
        rvAvengerMovies.layoutManager = LinearLayoutManager (this, LinearLayoutManager.HORIZONTAL, false)
        rvAvengerMovies.adapter = movieAdapter
    }

    fun showAvengerDetails (avengerView: AvengerView) {
        collapsingToolbar.title = avengerView.alias
        tvDetailAge.text = avengerView.age.toString()
        tvDetailDoB.text = avengerView.dob
        tvDetailStory.text = avengerView.story
        ivAvenger.loadUrlAndStartTransition(avengerView.image, this)
    }

    private fun renderAvengerMovies (avengerMovies: List<AvengerMovieView>?) {
        movieAdapter.listMovies = avengerMovies.orEmpty()
        hideProgress()
    }

    private fun handleFailure (failure: Failure?) {
        hideProgress()
        toast(failure.toString())
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
