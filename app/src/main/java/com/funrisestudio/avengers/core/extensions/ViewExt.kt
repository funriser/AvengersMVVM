package com.funrisestudio.avengers.core.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BaseTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.snackbar.Snackbar
import android.util.DisplayMetrics



fun ViewGroup.inflate(@LayoutRes layoutId: Int, root: ViewGroup = this, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layoutId, root, attachToRoot)

fun ImageView.loadFromUrl(url: String) =
        Glide.with(this.context.applicationContext).load(url).into(this)

fun ImageView.loadUrlAndStartTransition(url: String, fragment: Fragment) {
    val target: Target<Drawable> = ImageViewBaseTarget(this, fragment)
    Glide.with(context.applicationContext).load(url).into(target)
}

fun Context?.dip(dip: Int): Int {
    this?:return 0
    return dip * (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

fun Context?.dip(dip: Float): Float {
    this?:return 0f
    return dip * (resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

fun Fragment.dip(dip: Int): Int {
    return context.dip(dip)
}

fun Fragment.dip(dip: Float): Float {
    return context.dip(dip)
}

fun Fragment.popSnackbar(view: View, text: String = "Some text", duration: Int = Snackbar.LENGTH_LONG,
                         actionName: String = "", action: () -> Unit = {}) {
    val snackbar = Snackbar.make(view, text, duration)
    if (actionName.isNotEmpty() && action != {})
        snackbar.setAction(actionName) { action.invoke() }
    snackbar.show()
}

private class ImageViewBaseTarget(var imageView: ImageView?, var fragment: Fragment?) : BaseTarget<Drawable>() {
    override fun removeCallback(cb: SizeReadyCallback) {
        imageView = null
        fragment = null
    }

    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        resource.intrinsicWidth
        resource.intrinsicHeight
        imageView?.setImageDrawable(resource)
        fragment?.startPostponedEnterTransition()
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        super.onLoadFailed(errorDrawable)
        fragment?.startPostponedEnterTransition()
    }

    override fun getSize(cb: SizeReadyCallback) = cb.onSizeReady(SIZE_ORIGINAL, SIZE_ORIGINAL)
}