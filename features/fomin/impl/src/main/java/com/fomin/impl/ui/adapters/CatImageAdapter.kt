package com.fomin.impl.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.fomin.api.domain.models.CatImage
import com.fomin.impl.R
import com.imageloader.api.ImageLoader

internal class CatImageAdapter(
    private val imageLoader: ImageLoader,
) : ListAdapter<CatImage, CatImageViewHolder>(Diff) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fomin_image, parent, false)
        return CatImageViewHolder(view, imageLoader)
    }

    override fun onBindViewHolder(holder: CatImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object Diff : DiffUtil.ItemCallback<CatImage>() {
        override fun areItemsTheSame(oldItem: CatImage, newItem: CatImage) = 
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CatImage, newItem: CatImage) = 
            oldItem == newItem
    }
}


