package com.funrisestudio.avengers.app.avengers

import android.view.ViewTreeObserver

class AvengersAnimator {

    internal fun setUpReturnTransition(fragment: AvengersFragment) {
        with(fragment) {
            postponeEnterTransition()
            view?.viewTreeObserver?.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    startPostponedEnterTransition()
                    view?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }

}