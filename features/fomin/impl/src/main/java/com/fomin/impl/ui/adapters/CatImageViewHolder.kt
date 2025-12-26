package com.fomin.impl.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fomin.api.domain.models.CatImage
import com.fomin.impl.databinding.ItemFominImageBinding
import com.imageloader.api.ImageLoader

internal class CatImageViewHolder(
    view: View,
    private val imageLoader: ImageLoader,
) : RecyclerView.ViewHolder(view) {
    
    private val b = ItemFominImageBinding.bind(view)

    fun bind(image: CatImage) {
        imageLoader.load(b.catImage, image.url)
    }
}


