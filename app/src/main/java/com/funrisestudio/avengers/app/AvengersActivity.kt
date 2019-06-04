package com.funrisestudio.avengers.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.funrisestudio.avengers.R

import kotlinx.android.synthetic.main.fragment_avengers.*

class AvengersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

}