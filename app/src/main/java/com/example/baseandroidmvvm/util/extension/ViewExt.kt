package com.example.baseandroidmvvm.util.extension

import android.view.View
import com.example.baseandroidmvvm.util.helper.SingleClickListener

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.setOnSingleClickListener(action: (view: View) -> Unit) {
    setOnClickListener(SingleClickListener(action))
}