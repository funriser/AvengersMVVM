package com.funrisestudio.avengers.core.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import com.funrisestudio.avengers.App
import com.funrisestudio.avengers.R
import com.funrisestudio.avengers.core.Navigator
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity () {

    @Inject lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        App.appComponent.inject(this)
    }

    protected fun showProgress () {
        findViewById<ProgressBar>(R.id.progressSpinner)?.visibility = View.VISIBLE
    }

    protected fun hideProgress () {
        findViewById<ProgressBar>(R.id.progressSpinner)?.visibility = View.GONE
    }

}