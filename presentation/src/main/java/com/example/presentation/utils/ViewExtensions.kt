package com.example.presentation.utils

import android.view.View


fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.selectVisibility(isVisible: Boolean, isGone: Boolean = false) {
    if (isVisible) {
        visible()
    } else {
        if (isGone) gone() else invisible()
    }
}
