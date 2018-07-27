package com.funrisestudio.avengers.core.base

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.funrisestudio.avengers.R

open class BaseFragment : Fragment () {

    @LayoutRes  protected open val layoutId =             R.layout.fragment_avengers
    @IdRes      protected open val progressSpinnerId =    R.id.progressSpinner

    private var progressSpinner: ProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutId, container, false)
        progressSpinner = view.findViewById(progressSpinnerId)
        return view
    }

    protected fun showProgress () {
        progressSpinner?.visibility = View.VISIBLE
    }

    protected fun hideProgress () {
        progressSpinner?.visibility = View.GONE
    }

}