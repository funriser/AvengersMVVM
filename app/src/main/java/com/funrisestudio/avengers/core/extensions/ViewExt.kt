package com.funrisestudio.avengers.core.extensions

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun Context.toast (message: String = "Hello", length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun ViewGroup.inflate (@LayoutRes layoutId: Int, root: ViewGroup = this, attachToRoot: Boolean = false): View
        = LayoutInflater.from(context).inflate(layoutId, root, attachToRoot)

fun ImageView.loadFromUrl(url: String) =
        Glide.with(this.context.applicationContext).load(url).into(this)