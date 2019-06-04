package com.funrisestudio.avengers.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.funrisestudio.avengers.R

abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    @LayoutRes
    open var layoutId = R.layout.layout_empty

    protected fun showProgress() {
        view?.findViewById<ProgressBar>(R.id.progressSpinner)?.visibility = View.VISIBLE
    }

    protected fun hideProgress() {
        view?.findViewById<ProgressBar>(R.id.progressSpinner)?.visibility = View.GONE
    }

}