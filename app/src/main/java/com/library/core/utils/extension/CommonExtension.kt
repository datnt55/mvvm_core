package com.library.core.utils.extension

import android.content.res.Resources

fun Int.toPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

fun String.toInt(): Int {
    return Integer.parseInt(this)
}
