package com.coil.data

import android.content.Context
import android.widget.ImageView
import coil3.load
import com.coil.api.ImageLoader


internal class CoilImageLoader(
    private val context: Context
) : ImageLoader {

    override fun load(view: Any, url: String) {
        val imageView = view as ImageView
        imageView.load(url)
    }
}