package com.example.presentation.utils

import android.content.res.Resources
import android.util.TypedValue

fun dpToPx(dp: Int): Int {
    val r = Resources.getSystem()
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics)
        .toInt()
}