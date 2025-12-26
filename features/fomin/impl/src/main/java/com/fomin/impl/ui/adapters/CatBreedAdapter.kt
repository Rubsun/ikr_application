package com.fomin.impl.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.fomin.api.domain.models.CatBreed
import com.fomin.impl.R
import com.imageloader.api.ImageLoader

internal class CatBreedAdapter(
    private val imageLoader: ImageLoader,
    private val onBreedClick: (CatBreed) -> Unit,
) : ListAdapter<CatBreed, CatBreedViewHolder>(Diff) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatBreedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fomin_breed, parent, false)
        return CatBreedViewHolder(view, imageLoader, onBreedClick)
    }

    override fun onBindViewHolder(holder: CatBreedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object Diff : DiffUtil.ItemCallback<CatBreed>() {
        override fun areItemsTheSame(oldItem: CatBreed, newItem: CatBreed) = 
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CatBreed, newItem: CatBreed) = 
            oldItem == newItem
    }
}

