package com.ikr.libs.imageloader

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter

/**
 * Реализация ImageLoader через Coil
 */
class CoilImageLoader : ImageLoader {
    @Composable
    override fun LoadImage(
        url: String?,
        contentDescription: String?,
        modifier: Modifier,
        placeholder: Any?,
        error: Any?
    ) {
        val painter = rememberAsyncImagePainter(
            model = url,
            placeholder = placeholder,
            error = error
        )
        
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    }
}

