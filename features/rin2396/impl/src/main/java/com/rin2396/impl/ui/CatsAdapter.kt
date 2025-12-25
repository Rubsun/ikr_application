package com.rin2396.impl.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rin2396.api.domain.models.CatModel
import com.rin2396.impl.databinding.ItemCatBinding

internal interface ImageLoader {
    fun loadImage(url: String, imageView: android.widget.ImageView)
}

internal class CatsAdapter(private val imageLoader: ImageLoader) : ListAdapter<CatModel, CatsAdapter.CatViewHolder>(CatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val binding = ItemCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CatViewHolder(private val binding: ItemCatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cat: CatModel) {
            imageLoader.loadImage(cat.imageUrl, binding.catImageView)
            binding.catTagTextView.text = "Tag: ${cat.tag}"
        }
    }

    private class CatDiffCallback : DiffUtil.ItemCallback<CatModel>() {
        override fun areItemsTheSame(oldItem: CatModel, newItem: CatModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CatModel, newItem: CatModel): Boolean {
            return oldItem == newItem
        }
    }
}
