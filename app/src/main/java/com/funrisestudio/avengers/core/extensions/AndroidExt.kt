package com.funrisestudio.avengers.core.extensions

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

fun Int.toDp (context: Context) = (this/(context.resources.displayMetrics.densityDpi/ DisplayMetrics.DENSITY_DEFAULT))

fun Float.toDp (context: Context) = (this/(context.resources.displayMetrics.densityDpi/ DisplayMetrics.DENSITY_DEFAULT))

fun Int.toPx (context: Context) = (this*(context.resources.displayMetrics.densityDpi/ DisplayMetrics.DENSITY_DEFAULT))

fun Float.toPx (context: Context) = (this*(context.resources.displayMetrics.densityDpi/ DisplayMetrics.DENSITY_DEFAULT))

fun DisplayMetrics.spToPx (sp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, this)

fun Context.getDisplayWidth () = this.resources.displayMetrics.widthPixels

fun Context.getDisplayHeight () = this.resources.displayMetrics.heightPixels