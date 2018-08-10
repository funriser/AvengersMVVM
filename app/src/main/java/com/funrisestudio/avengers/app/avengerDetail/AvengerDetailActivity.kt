package com.funrisestudio.avengers.app.avengerDetail

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import com.funrisestudio.avengers.R
import com.funrisestudio.avengers.app.view.AvengerView
import com.funrisestudio.avengers.core.extensions.loadUrlAndStartTransition

import kotlinx.android.synthetic.main.activity_avenger_detail.*
import kotlinx.android.synthetic.main.content_avenger_detail.*

class AvengerDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avenger_detail)
        setSupportActionBar(toolbar)
        window.sharedElementsUseOverlay = false

        initView()
        showAvengerDetails(intent.getParcelableExtra(AVENGER_DETAIL))
    }

    private fun initView () {
        supportPostponeEnterTransition()
        appBarDetail.addOnOffsetChangedListener(offsetChangeListener)
    }

    fun showAvengerDetails (avengerView: AvengerView) {
        tvDetailAlias.text = avengerView.alias
        tvDetailAge.text = avengerView.age.toString()
        tvDetailDoB.text = avengerView.dob
        tvDetailStory.text = avengerView.story
        ivAvenger.loadUrlAndStartTransition(avengerView.image, this)
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
