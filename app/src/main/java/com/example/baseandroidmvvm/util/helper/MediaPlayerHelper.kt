package com.example.baseandroidmvvm.util.helper

import android.content.Context
import android.media.MediaPlayer

class MediaPlayerHelper(private val context: Context) {
    private var mp: MediaPlayer? = null
    private var isLoadSuccess = false
    private var isLooping = false
    var isTimer = false

    fun setRawData(res: Int) {
        try {
            mp?.reset()
            mp = MediaPlayer.create(context, res).apply {
                setOnPreparedListener {
                    it.start()
                    mp?.setOnCompletionListener { mp?.start() }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun start(onComplete: (() -> Unit)? = null) {
        if (mp == null || !isLoadSuccess) return
        mp?.start()
        mp?.setOnCompletionListener {
            if ((isLooping() && isTimer) || !isTimer) {
                mp?.start()
            } else {
                onComplete?.invoke()
            }
        }
    }

    fun isPlaying(): Boolean = mp?.isPlaying ?: false

    private fun isLooping(): Boolean {
        return isLooping
    }

    fun setLooping(isLoop: Boolean) {
        isLooping = isLoop
    }

    fun pause() {
        if (mp != null) {
            mp?.pause()
        }
    }

    fun stopAndPrepare() {
        try {
            if (mp != null) {
                mp?.stop()
                mp?.prepareAsync()
            }
        } catch (ex: IllegalStateException) {
            ex.printStackTrace()
        }
    }

    fun stop() {
        if (mp != null) {
            mp?.stop()
        }
    }

    fun release() {
        mp?.release()
        mp = null
    }

    fun reset() {
        mp?.reset()
    }
}