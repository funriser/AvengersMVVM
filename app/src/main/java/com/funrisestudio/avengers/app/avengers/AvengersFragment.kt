package com.funrisestudio.avengers.app.avengers

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import com.funrisestudio.avengers.App
import com.funrisestudio.avengers.R
import com.funrisestudio.avengers.core.base.BaseFragment
import com.funrisestudio.avengers.core.di.viewmodel.ViewModelFactory
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.core.extensions.failure
import com.funrisestudio.avengers.core.extensions.observe
import com.funrisestudio.avengers.core.extensions.toast
import com.funrisestudio.avengers.core.extensions.viewModel
import com.funrisestudio.avengers.domain.entity.Avenger
import kotlinx.android.synthetic.main.fragment_avengers.*
import kotlinx.android.synthetic.main.item_card_avenger.*
import javax.inject.Inject

class AvengersFragment : BaseFragment () {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var avengersAdapter: AvengersAdapter

    private lateinit var viewModel: AvengersViewModel

    override val layoutId = R.layout.fragment_avengers

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
        showProgress()
        viewModel.getAvengers()
    }

    private fun initView () {
        rvAvengers.layoutManager = GridLayoutManager (context, 2)
        rvAvengers.itemAnimator = DefaultItemAnimator ()
        rvAvengers.adapter = avengersAdapter
    }

    private fun renderAvengers (list: List<Avenger>?) {
        avengersAdapter.collection = list.orEmpty()
        hideProgress()
    }

    private fun handleFailure (failure: Failure?) {
        hideProgress()
        context?.toast(failure.toString())
    }

}
