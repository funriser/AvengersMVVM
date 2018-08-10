package com.funrisestudio.avengers.core.base

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import com.funrisestudio.avengers.R

abstract class BaseActivity : AppCompatActivity () {

    protected fun showProgress () {
        findViewById<ProgressBar>(R.id.progressSpinner)?.visibility = View.VISIBLE
    }

    protected fun hideProgress () {
        findViewById<ProgressBar>(R.id.progressSpinner)?.visibility = View.GONE
    }

}