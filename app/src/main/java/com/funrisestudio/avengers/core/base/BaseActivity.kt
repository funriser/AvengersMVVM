package com.funrisestudio.avengers.core.base

import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import com.funrisestudio.avengers.R
import com.funrisestudio.avengers.core.Navigator
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity () {

    protected val navigator: Navigator by inject()

    protected fun showProgress () {
        findViewById<ProgressBar>(R.id.progressSpinner)?.visibility = View.VISIBLE
    }

    protected fun hideProgress () {
        findViewById<ProgressBar>(R.id.progressSpinner)?.visibility = View.GONE
    }

}