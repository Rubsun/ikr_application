package com.imageloader.api

interface ImageLoader {
    fun load(
        view: Any,
        url: String
    )
    
    fun loadWithCrossfade(
        view: Any,
        url: String,
        crossfade: Boolean = true
    )
}
