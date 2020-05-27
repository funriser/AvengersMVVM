package com.funrisestudio.avengers.app.avengers

import android.os.Bundle

import android.widget.Toast
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.funrisestudio.avengers.R
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            observe(avengers, ::renderAvengers)
            observe(progress, ::renderProgress)
            failure(failure, ::handleFailure)
        }
        initView()
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
        bar.setNavigationOnClickListener {
            BottomSheetOptionsFragment().show(childFragmentManager, null)
        }
        fab.setOnClickListener {
            Toast.makeText(context, "In progress", Toast.LENGTH_SHORT).show()
        }
    }

    private fun renderAvengers(list: List<AvengerView>?) {
        avengersAdapter.collection = list.orEmpty()
    }

    private fun renderProgress(isShown: Boolean?) {
        if (isShown == true) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection ->
                popSnackbar(layoutMain,
                        getString(R.string.error_network), Snackbar.LENGTH_INDEFINITE,
                        getString(R.string.error_try_again)) { viewModel.loadAvengers() }
            else -> popSnackbar(layoutMain, getString(R.string.error_server))
        }
    }

}