package com.funrisestudio.avengers.app.avengers

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.funrisestudio.avengers.R
import com.funrisestudio.avengers.app.view.AvengerView
import com.funrisestudio.avengers.core.base.BaseActivity
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.core.extensions.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AvengersActivity : BaseActivity() {

    private val avengersAdapter: AvengersAdapter by inject()

    private val viewModel: AvengersViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        with(viewModel) {
            observe(avengers, ::renderAvengers)
            failure(failure, ::handleFailure)
        }
        initView()
        loadAvengers()
    }

    private fun loadAvengers() {
        showProgress()
        viewModel.getAvengers()
    }

    private fun initView() {
        rvAvengers.layoutManager = GridLayoutManager(this, 2)
        rvAvengers.itemAnimator = DefaultItemAnimator()
        rvAvengers.adapter = avengersAdapter
        avengersAdapter.clickListener = { avInfo, navExtras ->
            navigator.goToAvengerDetails(this, avInfo, navExtras)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                toast(getString(R.string.author)); true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
