package com.funrisestudio.avengers.app.avengers

import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.funrisestudio.avengers.App
import com.funrisestudio.avengers.R
import com.funrisestudio.avengers.core.di.viewmodel.ViewModelFactory
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.core.extensions.failure
import com.funrisestudio.avengers.core.extensions.observe
import com.funrisestudio.avengers.core.extensions.toast
import com.funrisestudio.avengers.core.extensions.viewModel
import com.funrisestudio.avengers.domain.entity.Avenger
import kotlinx.android.synthetic.main.fragment_avengers.*
import javax.inject.Inject

class AvengersFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var avengersAdapter: AvengersAdapter

    private lateinit var viewModel: AvengersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        viewModel = viewModel(viewModelFactory) {
            observe(avengers, ::renderAvengers)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadAvengers()
    }

    private fun loadAvengers () {
        progressBar.visibility = View.VISIBLE
        viewModel.getAvengers()
    }

    private fun initView () {
        rvAvengers.layoutManager = GridLayoutManager (context, 2)
        rvAvengers.itemAnimator = DefaultItemAnimator ()
        rvAvengers.adapter = avengersAdapter
    }

    private fun renderAvengers (list: List<Avenger>?) {
        progressBar.visibility = View.GONE
        avengersAdapter.collection = list.orEmpty()
    }

    private fun handleFailure (failure: Failure?) {
        progressBar.visibility = View.GONE
        context?.toast("Error")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_avengers, container, false)
    }

}
