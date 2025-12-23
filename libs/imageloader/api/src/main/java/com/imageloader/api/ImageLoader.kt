package com.imageloader.api

interface ImageLoader {
    fun load(
        view: Any,
        url: String
    )
}
