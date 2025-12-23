package com.example.libs.demyanenkocoil

import android.widget.ImageView
import coil3.load


internal class CoilDemyanenkoImageLoader : DemyanenkoImageLoader {
    override fun load(imageView: ImageView, data: Any?) {
        imageView.load(data)
    }
}
