package com.example.libs

import android.widget.ImageView
import coil3.load
import com.example.demyanenkocoil.api.DemyanenkoImageLoader


internal class CoilDemyanenkoImageLoader : DemyanenkoImageLoader {
    override fun load(imageView: Any, data: Any?) {
        val imageView = imageView as ImageView
        imageView.load(data)
    }
}
