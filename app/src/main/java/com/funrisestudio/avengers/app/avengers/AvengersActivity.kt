package com.funrisestudio.avengers.app.avengers

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.funrisestudio.avengers.App
import com.funrisestudio.avengers.R
import com.funrisestudio.avengers.app.view.AvengerView
import com.funrisestudio.avengers.core.base.BaseActivity
import com.funrisestudio.avengers.core.di.viewmodel.ViewModelFactory
import com.funrisestudio.avengers.core.exception.Failure
import com.funrisestudio.avengers.core.extensions.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class AvengersActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var avengersAdapter: AvengersAdapter

    private lateinit var viewModel: AvengersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        App.appComponent.inject(this)

        viewModel = viewModel(viewModelFactory) {
            observe(avengers, ::renderAvengers)
            failure(failure, ::handleFailure)
        }

        initView()
        loadAvengers()
    }

    private fun loadAvengers () {
        showProgress()
        viewModel.getAvengers()
    }

    private fun initView () {
        rvAvengers.layoutManager = GridLayoutManager (this, 2)
        rvAvengers.itemAnimator = DefaultItemAnimator ()
        rvAvengers.adapter = avengersAdapter
        avengersAdapter.clickListener = { avInfo, navExtras ->
            navigator.goToAvengerDetails(this, avInfo, navExtras)
        }
    }

    private fun renderAvengers (list: List<AvengerView>?) {
        avengersAdapter.collection = list.orEmpty()
        hideProgress()
    }

    private fun handleFailure (failure: Failure?) {
        hideProgress()
        toast(failure.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings ->  { toast(getString(R.string.author)) ; true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
