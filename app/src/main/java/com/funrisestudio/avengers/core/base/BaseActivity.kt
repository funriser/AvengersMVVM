package com.funrisestudio.avengers.core.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import com.funrisestudio.avengers.R

abstract class BaseActivity : AppCompatActivity () {

    private var progressSpinner: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressSpinner = findViewById(R.id.progressSpinner)
    }

    protected fun showProgress () {
        progressSpinner?.visibility = View.VISIBLE
    }

    protected fun hideProgress () {
        progressSpinner?.visibility = View.GONE
    }


}