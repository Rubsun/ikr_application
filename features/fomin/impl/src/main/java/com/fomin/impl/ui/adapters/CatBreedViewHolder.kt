package com.fomin.impl.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fomin.api.domain.models.CatBreed
import com.fomin.impl.databinding.ItemFominBreedBinding
import com.imageloader.api.ImageLoader

internal class CatBreedViewHolder(
    view: View,
    private val imageLoader: ImageLoader,
    private val onBreedClick: (CatBreed) -> Unit,
) : RecyclerView.ViewHolder(view) {
    
    private val b = ItemFominBreedBinding.bind(view)

    fun bind(breed: CatBreed) {
        b.breedName.text = breed.name
        b.breedOrigin.text = breed.origin ?: ""
        b.breedDescription.text = breed.description ?: ""
        
        val imageUrl = breed.imageUrl
        if (imageUrl != null) {
            imageLoader.load(b.breedImage, imageUrl)
        } else {
            b.breedImage.setImageDrawable(null)
        }

        itemView.setOnClickListener {
            onBreedClick(breed)
        }
    }
}

