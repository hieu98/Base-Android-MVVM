package com.example.baseandroidmvvm.util.helper

import android.annotation.SuppressLint
import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.mediacodec.MediaCodecInfo
import androidx.media3.exoplayer.mediacodec.MediaCodecSelector
import androidx.media3.ui.PlayerView
import java.util.Collections

class VideoPlayer (
    private val context: Context,
) : Player.Listener {
    var exoPlayer: ExoPlayer? = null
    var onErrorListener: (() -> Unit)? = null
    var urlPlay: String = ""

    init {
        exoPlayer = ExoPlayer.Builder(context).build()
    }

    @SuppressLint("UnsafeOptInUsageError")
    fun startVideo(url: String, playerView: PlayerView) {
        urlPlay = url
        playerView.apply {
            player = null
            player = exoPlayer
            player?.repeatMode = Player.REPEAT_MODE_ONE
        }

        val mediaItem = MediaItem.fromUri(url)

        try {
            exoPlayer?.setMediaItem(mediaItem)
            exoPlayer?.playWhenReady = false
            exoPlayer?.volume = 0f
            exoPlayer?.prepare()
            exoPlayer?.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                    if (playbackState == Player.STATE_ENDED) {
                        exoPlayer?.seekTo(0)
                    }

                    if (playbackState == Player.STATE_IDLE ) { exoPlayer?.stop() }

                }

                override fun onPlayerError(error: PlaybackException) {
                    super.onPlayerError(error)
                    restartVideoWhenError(mediaItem)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun restartVideoWhenError(mediaItem: MediaItem) {
        val renderersFactory: DefaultRenderersFactory =
            DefaultRenderersFactory(context.applicationContext)
                .setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER)
                .setMediaCodecSelector { mimeType, requiresSecureDecoder, requiresTunnelingDecoder ->
                    var decoder: List<MediaCodecInfo> =
                        mimeType.let {
                            MediaCodecSelector.DEFAULT
                                .getDecoderInfos(
                                    it,
                                    requiresSecureDecoder,
                                    requiresTunnelingDecoder
                                )
                        }
                    if (MimeTypes.VIDEO_H264 == mimeType) {
                        decoder = ArrayList(decoder)
                        Collections.reverse(decoder)
                    }
                    decoder
                }

        exoPlayer = ExoPlayer.Builder(context, renderersFactory).build().also {
            onErrorListener?.invoke()
        }

        exoPlayer?.setMediaItem(mediaItem)
        exoPlayer?.playWhenReady = false
        exoPlayer?.volume = 0f
        exoPlayer?.prepare()
    }

    fun pauseVideo() {
        exoPlayer?.seekTo(0)
        exoPlayer?.pause()
    }

    fun playVideo() {
        exoPlayer?.play()
    }

    fun speedVideo(speed: Float) {
        exoPlayer?.setPlaybackSpeed(speed)
    }
}