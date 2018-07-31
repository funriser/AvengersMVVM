package com.funrisestudio.avengers.app

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.funrisestudio.avengers.R
import com.funrisestudio.avengers.app.avengerdetail.AvengerDetailFragment
import com.funrisestudio.avengers.app.avengers.AvengersFragment
import com.funrisestudio.avengers.core.Screen
import com.funrisestudio.avengers.core.extensions.transaction

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        addFragment(Screen.AVENGERS)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun addFragment (screen: Screen) {
        supportFragmentManager.transaction {
            replace(R.id.containerScreen, resolveScreen(screen))
            addToBackStack(screen.name)
        }
    }

    private fun resolveScreen (screen: Screen): Fragment =
            when (screen) {
                Screen.AVENGERS -> AvengersFragment ()
                Screen.AVENGER_DETAIL -> AvengerDetailFragment ()
            }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
