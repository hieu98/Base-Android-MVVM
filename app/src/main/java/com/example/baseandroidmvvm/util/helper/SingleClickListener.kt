package com.example.baseandroidmvvm.util.helper

import android.os.SystemClock
import android.view.View

class SingleClickListener(
    private val action: (view: View) -> Unit
) : View.OnClickListener {

    private val delayMs = 500
    private var lastTime = 0L

    override fun onClick(view: View) {
        val current = SystemClock.elapsedRealtime()
        if (current - lastTime < delayMs) {
            return
        }
        lastTime = current
        action.invoke(view)
    }
}