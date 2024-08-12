package com.example.baseandroidmvvm.util.helper

import android.app.Activity
import android.app.ProgressDialog
import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.baseandroidmvvm.R
import com.example.baseandroidmvvm.data.model.WallpaperMode
import kotlin.concurrent.thread

object WallpaperHelper {

    fun setWallpaper(
        context: Activity,
        path: String,
        wallpaperMode: WallpaperMode,
        callback: (() -> Unit)? = null
    ) {
        try {
            val screenWidth = context.resources.displayMetrics.widthPixels
            val screenHeight = context.resources.displayMetrics.heightPixels

            val wallpaperManager = WallpaperManager.getInstance(context)
            wallpaperManager.suggestDesiredDimensions(screenWidth, screenHeight)
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, path.toUri()))
            } else {
                MediaStore.Images.Media.getBitmap(context.contentResolver, path.toUri())
            }
            setWallPaper(
                context,
                wallpaperManager,
                bitmap,
                wallpaperMode,
                callback
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setWallPaper(
        context: Activity,
        wallpaperManager: WallpaperManager,
        bitmap: Bitmap,
        wallpaperMode: WallpaperMode,
        callback: (() -> Unit)? = null,
    ) {
        try {
            if (!wallpaperManager.isWallpaperSupported) {
                Toast.makeText(
                    context,
                    context.getString(R.string.des_unsupported_wallpaper),
                    Toast.LENGTH_LONG
                )
                    .show()
                return
            }

            val screenWidth = context.resources.displayMetrics.widthPixels
            val screenHeight = context.resources.displayMetrics.heightPixels

            val bitmapFinal =
                Bitmap.createScaledBitmap(bitmap, screenWidth, screenHeight, true)

            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle(context.getString(R.string.des_loading_set_wallpaper))
            progressDialog.show()
            progressDialog.setCancelable(false)
            thread {
                when (wallpaperMode) {
                    WallpaperMode.HOME -> {
                        wallpaperManager.setBitmap(
                            bitmapFinal,
                            null,
                            true,
                            WallpaperManager.FLAG_SYSTEM
                        )
                    }

                    WallpaperMode.LOCK -> {
                        wallpaperManager.setBitmap(
                            bitmapFinal,
                            null,
                            true,
                            WallpaperManager.FLAG_LOCK
                        )
                    }

                    else -> {
                        wallpaperManager.setBitmap(
                            bitmapFinal,
                            null,
                            true,
                            WallpaperManager.FLAG_SYSTEM
                        )

                        wallpaperManager.setBitmap(
                            bitmapFinal,
                            null,
                            true,
                            WallpaperManager.FLAG_LOCK
                        )
                    }
                }
                ContextCompat.getMainExecutor(context).execute {
                    if (progressDialog.isShowing
                        && context.isFinishing.not()
                        && context.isDestroyed.not()
                    ) {
                        progressDialog.dismiss()
                        callback?.invoke()
                    }
                }
            }.start()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setWallPaperBelowApi24(
        context: Activity,
        url: String,
    ) {
        try {
            val intent = Intent("android.intent.action.ATTACH_DATA")
            intent.addCategory("android.intent.category.DEFAULT")
            val str = "image/*"
            intent.setDataAndType(url.toUri(), str)
            intent.putExtra("mimeType", str)
            context.startActivity(Intent.createChooser(intent, "Set As:"))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}