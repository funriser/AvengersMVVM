package com.funrisestudio.avengers.app

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.funrisestudio.avengers.R

/**
 * A placeholder fragment containing a simple view.
 */
class AvengersFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_avengers, container, false)
    }
}
