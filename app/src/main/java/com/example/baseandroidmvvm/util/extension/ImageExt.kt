package com.example.baseandroidmvvm.util.extension

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.example.baseandroidmvvm.R
import java.io.File

private fun ImageView.getPlaceHolder(): Drawable? {
    return ContextCompat.getDrawable(context, R.drawable.bg_place_holder)
}

fun ImageView.loadImage(path: String) {
    val requestBuilder: RequestBuilder<Drawable> =
        Glide.with(this).asDrawable().sizeMultiplier(0.1f)
    Glide.with(this).load(File(path)).placeholder(R.drawable.bg_place_holder).thumbnail(requestBuilder)
        .into(this)
}

fun ImageView.loadImage(@DrawableRes icon: Int) {
    val requestBuilder: RequestBuilder<Drawable> = Glide.with(this)
        .asDrawable().sizeMultiplier(0.1f)

    Glide.with(this)
        .load(icon)
        .thumbnail(requestBuilder)
        .into(this)
}

fun ImageView.loadUri(uri: Uri) {
    Glide.with(context)
        .load(uri)
        .into(this)
}