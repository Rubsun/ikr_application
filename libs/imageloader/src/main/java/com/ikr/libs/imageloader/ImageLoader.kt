package com.ikr.libs.imageloader

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Абстракция для загрузки изображений
 */
interface ImageLoader {
    @Composable
    fun LoadImage(
        url: String?,
        contentDescription: String?,
        modifier: Modifier = Modifier,
        placeholder: Any? = null,
        error: Any? = null
    )
}

