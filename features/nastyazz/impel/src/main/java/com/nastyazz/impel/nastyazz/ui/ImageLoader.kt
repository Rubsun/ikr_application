package com.nastyazz.impel.nastyazz.ui

import android.widget.ImageView

interface ImageLoader {
    fun load(url: String, target: ImageView)
}
