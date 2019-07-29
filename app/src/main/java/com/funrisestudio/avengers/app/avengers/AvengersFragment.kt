package com.funrisestudio.avengers.app.avengers

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater

import android.view.View
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.funrisestudio.avengers.R
import com.funrisestudio.avengers.app.AvengersActivity
import com.funrisestudio.avengers.app.view.AvengerView
import com.funrisestudio.avengers.core.base.BaseFragment
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.core.extensions.failure
import com.funrisestudio.avengers.core.extensions.observe
import com.funrisestudio.avengers.core.extensions.popSnackbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.content_avengers.*
import kotlinx.android.synthetic.main.fragment_avengers.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AvengersFragment : BaseFragment() {

    private val avengersAdapter: AvengersAdapter by inject()
    private val avengersAnimator: AvengersAnimator by inject()
    private val viewModel: AvengersViewModel by viewModel()

    override var layoutId: Int = R.layout.fragment_avengers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            observe(avengers, ::renderAvengers)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        loadAvengers()
    }

    private fun loadAvengers() {
        showProgress()
        viewModel.getAvengers()
    }

    private fun initView() {
        avengersAnimator.setUpReturnTransition(this)
        rvAvengers.layoutManager = GridLayoutManager(context, 2)
        rvAvengers.itemAnimator = DefaultItemAnimator()
        rvAvengers.adapter = avengersAdapter
        avengersAdapter.clickListener = { avInfo, trView ->
            val navExtras = FragmentNavigatorExtras(
                    trView to trView.transitionName)
            val action = AvengersFragmentDirections.actionAvengersToDetails(avInfo,
                    trView.transitionName)
            findNavController().navigate(action, navExtras)
        }
    }

    private fun renderAvengers(list: List<AvengerView>?) {
        avengersAdapter.collection = list.orEmpty()
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        hideProgress()
        when (failure) {
            is Failure.NetworkConnection ->
                popSnackbar(layoutMain,
                        getString(R.string.error_network), Snackbar.LENGTH_INDEFINITE,
                        getString(R.string.error_try_again)) { loadAvengers() }
            else -> popSnackbar(layoutMain, getString(R.string.error_server))
        }
    }

}