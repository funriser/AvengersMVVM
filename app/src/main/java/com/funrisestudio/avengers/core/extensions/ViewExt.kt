package com.funrisestudio.avengers.core.extensions

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

fun Context.toast (message: String = "Hello", length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun ViewGroup.inflate (@LayoutRes layoutId: Int, root: ViewGroup = this, attachToRoot: Boolean = false): View
        = LayoutInflater.from(this.context).inflate(layoutId, root, attachToRoot)