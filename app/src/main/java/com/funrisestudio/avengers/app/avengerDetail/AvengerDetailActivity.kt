package com.funrisestudio.avengers.app.avengerDetail

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

class AvengerDetailActivity : BaseActivity () {

    @Inject lateinit var viewModelFactory: ViewModelFactory

    @Inject lateinit var movieAdapter: AvengerMoviesAdapter

    private lateinit var viewModel: AvengerDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avenger_detail)
        setSupportActionBar(toolbar)
        App.appComponent.inject(this)
        window.sharedElementsUseOverlay = false

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
        supportPostponeEnterTransition()
        appBarDetail.addOnOffsetChangedListener(offsetChangeListener)
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

    private val offsetChangeListener = object: AppBarLayout.OnOffsetChangedListener {
        val showImagePercentage = 20
        var mMaxScrollSize = 0
        var mIsImageHidden = false

        override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
            if (mMaxScrollSize == 0)
                mMaxScrollSize = appBarLayout.totalScrollRange
            val currentScrollPercentage = Math.abs(i) * 100 / mMaxScrollSize
            if (currentScrollPercentage >= showImagePercentage) {
                if (!mIsImageHidden) {
                    mIsImageHidden = true
                    ViewCompat.animate(fab).scaleY(0f).scaleX(0f).start()
                }
            }
            if (currentScrollPercentage < showImagePercentage) {
                if (mIsImageHidden) {
                    mIsImageHidden = false
                    ViewCompat.animate(fab).scaleY(1f).scaleX(1f).start()
                }
            }
        }
    }

    override fun onBackPressed() {
        window.sharedElementsUseOverlay = true
        super.onBackPressed()
    }

    companion object {

        const val AVENGER_DETAIL = "avengerDetail"

    }

}
