package com.coil.data

import android.content.Context
import coil3.load
import com.coil.api.ImageLoader

import android.widget.ImageView





internal class CoilImageLoader(
    private val context: Context
) : ImageLoader {

    override fun load(view: Any, url: String) {
        val imageView = view as ImageView
        imageView.load(url)
    }
}