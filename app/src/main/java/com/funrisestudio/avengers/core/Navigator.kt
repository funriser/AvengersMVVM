package com.funrisestudio.avengers.core

import androidx.core.app.ActivityOptionsCompat
import android.view.View
import com.funrisestudio.avengers.app.view.AvengerView

class Navigator {

    /*fun goToAvengerDetails (from: androidx.fragment.app.FragmentActivity, transfer: AvengerView, extras: Navigator.Extras? = null) {
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

    class Extras(val transitionSharedElement: View)*/

}