package com.funrisestudio.avengers.core

import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.FragmentActivity
import android.view.View
import com.funrisestudio.avengers.app.avengerDetail.AvengerDetailActivity
import com.funrisestudio.avengers.app.view.AvengerView

class Navigator {

    fun goToAvengerDetails (from: FragmentActivity, transfer: AvengerView, extras: Navigator.Extras? = null) {
        val intent = AvengerDetailActivity.callingIntent(from, transfer)
        if (extras == null) {
            from.startActivity(intent)
        } else {
            val sharedElement = extras.transitionSharedElement
            val activityOptions = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(from, sharedElement, sharedElement.transitionName)
            from.startActivity(intent, activityOptions.toBundle())
        }
    }

    class Extras(val transitionSharedElement: View)

}