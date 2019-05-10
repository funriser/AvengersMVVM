package com.funrisestudio.avengers.core.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.LayoutRes
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BaseTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition

fun Context.toast (message: String = "Hello", length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun ViewGroup.inflate (@LayoutRes layoutId: Int, root: ViewGroup = this, attachToRoot: Boolean = false): View
        = LayoutInflater.from(context).inflate(layoutId, root, attachToRoot)

fun ImageView.loadFromUrl(url: String) =
        Glide.with(this.context.applicationContext).load(url).into(this)

fun ImageView.loadUrlAndStartTransition(url: String, activity: androidx.fragment.app.FragmentActivity) {
    val target: Target<Drawable> = ImageViewBaseTarget(this,
            activity)
    Glide.with(context.applicationContext).load(url).into(target)
}

fun Context.popSnackbar (view: View, text: String = "Some text", duration: Int = Snackbar.LENGTH_LONG, actionName: String = "", action: () -> Unit = {}) {
    val snackbar = Snackbar.make(view, text, duration)
    if (actionName.isNotEmpty() && action != {})
        snackbar.setAction(actionName) { action.invoke() }
    snackbar.show()
}

private class ImageViewBaseTarget (var imageView: ImageView?, var activity: androidx.fragment.app.FragmentActivity?) : BaseTarget<Drawable>() {
    override fun removeCallback(cb: SizeReadyCallback) {
        imageView = null
        activity = null
    }

    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        resource.intrinsicWidth
        resource.intrinsicHeight
        imageView?.setImageDrawable(resource)
        activity?.supportStartPostponedEnterTransition()
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        super.onLoadFailed(errorDrawable)
        activity?.supportStartPostponedEnterTransition()
    }

    override fun getSize(cb: SizeReadyCallback) = cb.onSizeReady(SIZE_ORIGINAL, SIZE_ORIGINAL)
}