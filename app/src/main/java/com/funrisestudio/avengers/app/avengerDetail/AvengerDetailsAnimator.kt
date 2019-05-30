package com.funrisestudio.avengers.app.avengerDetail

import com.google.android.material.appbar.AppBarLayout
import androidx.core.view.ViewCompat
import android.view.View

class AvengerDetailsAnimator {

    companion object {
        private const val SCALE_UP_VALUE = 1.0F
        private const val SCALE_DOWN_VALUE = 0.0F
    }

    private var appBarOffsetChangedListener: AppBarLayout.OnOffsetChangedListener? = null
    private var appBarSlideListener: AppBarSlideListener? = null

    internal fun enableOverlayForTransition (activity: AvengerDetailActivity) {
        activity.window.sharedElementsUseOverlay = true
    }

    internal fun disableOverlayForTransition (activity: AvengerDetailActivity) {
        activity.window.sharedElementsUseOverlay = false
    }

    internal fun postponeTransition (activity: AvengerDetailActivity) {
        activity.supportPostponeEnterTransition()
    }

    internal fun scaleUpView (view: View) = scaleView(view, SCALE_UP_VALUE, SCALE_UP_VALUE)

    internal fun scaleDownView (view: View) = scaleView(view, SCALE_DOWN_VALUE, SCALE_DOWN_VALUE)

    private fun scaleView(view: View, x: Float, y: Float) =
            ViewCompat.animate(view).scaleX(x).scaleY(y).start()

    internal fun setAppBarSlideListener (appBarLayout: AppBarLayout, slideListener: AppBarSlideListener, percent: Int) {
        appBarSlideListener = slideListener
        appBarOffsetChangedListener = AppBarPercentageSlideListener(percent)
        appBarLayout.addOnOffsetChangedListener(appBarOffsetChangedListener)
    }

    internal fun removeAppBarSlideListener (appBarLayout: AppBarLayout) {
        appBarLayout.removeOnOffsetChangedListener(appBarOffsetChangedListener)
        appBarSlideListener = null
    }

    inner class AppBarPercentageSlideListener (private val showingPercentage: Int)
        : AppBarLayout.OnOffsetChangedListener {

        private var maxScrollSize = 0
        private var isBarHidden = false

        override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
            if (maxScrollSize == 0)
                maxScrollSize = appBarLayout.totalScrollRange
            val currentScrollPercentage = Math.abs(verticalOffset) * 100 / maxScrollSize
            if (currentScrollPercentage >= showingPercentage) {
                if (!isBarHidden) {
                    isBarHidden = true
                    appBarSlideListener?.onAppBarOpened()
                }
            }
            if (currentScrollPercentage < showingPercentage) {
                if (isBarHidden) {
                    isBarHidden = false
                    appBarSlideListener?.onAppBarClosed()
                }
            }
        }
    }

    interface AppBarSlideListener {

        fun onAppBarOpened ()

        fun onAppBarClosed ()

    }

}