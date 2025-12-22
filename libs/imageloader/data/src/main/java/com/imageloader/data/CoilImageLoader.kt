package com.imageloader.data

import android.content.Context
import coil3.load

import android.widget.ImageView
import com.imageloader.api.ImageLoader


internal class CoilImageLoader(
    private val context: Context
) : ImageLoader {

    override fun load(view: Any, url: String) {
        val imageView = view as ImageView
        imageView.load(url)
    }
}