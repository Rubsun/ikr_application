package com.imageloader.data

import android.content.Context
import android.widget.ImageView
import coil3.load
import coil3.request.crossfade
import com.imageloader.api.ImageLoader

internal class CoilImageLoader(
    private val context: Context
) : ImageLoader {

    override fun load(view: Any, url: String) {
        val imageView = view as ImageView
        imageView.load(url)
    }
    
    override fun loadWithCrossfade(view: Any, url: String, crossfade: Boolean) {
        val imageView = view as ImageView
        imageView.load(url) {
            if (crossfade) {
                crossfade(true)
            }
        }
    }
}