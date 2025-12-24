package com.dimmension.imageloader.data

import android.widget.ImageView
import coil3.load
import coil3.request.error
import coil3.request.placeholder
import com.dimmension.imageloader.api.DimmensionImageLoader

internal class CoilImageLoaderImpl : DimmensionImageLoader {

    override fun load(view: Any, url: String) {
        val imageView = view as ImageView
        imageView.load(url)
    }

    override fun load(view: Any, url: String, placeholderResId: Int?, errorResId: Int?) {
        val imageView = view as ImageView
        if (placeholderResId != null || errorResId != null) {
            imageView.load(url) {
                if (placeholderResId != null) {
                    placeholder(placeholderResId)
                }
                if (errorResId != null) {
                    error(errorResId)
                }
            }
        } else {
            imageView.load(url)
        }
    }
}

