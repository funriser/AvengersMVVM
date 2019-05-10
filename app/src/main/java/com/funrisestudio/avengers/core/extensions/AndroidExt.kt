package com.funrisestudio.avengers.core.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.TypedValue

inline fun <reified T : ViewModel> AppCompatActivity.viewModel(factory: ViewModelProvider.Factory, body: T.() -> Unit): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}

fun Int.toDp (context: Context) = (this/(context.resources.displayMetrics.densityDpi/ DisplayMetrics.DENSITY_DEFAULT))

fun Float.toDp (context: Context) = (this/(context.resources.displayMetrics.densityDpi/ DisplayMetrics.DENSITY_DEFAULT))

fun Int.toPx (context: Context) = (this*(context.resources.displayMetrics.densityDpi/ DisplayMetrics.DENSITY_DEFAULT))

fun Float.toPx (context: Context) = (this*(context.resources.displayMetrics.densityDpi/ DisplayMetrics.DENSITY_DEFAULT))

fun DisplayMetrics.spToPx (sp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, this)

fun Context.getDisplayWidth () = this.resources.displayMetrics.widthPixels

fun Context.getDisplayHeight () = this.resources.displayMetrics.heightPixels