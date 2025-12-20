package com.dyatlova.impl.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.dyatlova.impl.databinding.ItemDyatlovaDestinationBinding

internal class DestinationAdapter :
    ListAdapter<DestinationUi, DestinationAdapter.DestinationViewHolder>(DestinationDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDyatlovaDestinationBinding.inflate(inflater, parent, false)
        return DestinationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DestinationViewHolder(
        private val binding: ItemDyatlovaDestinationBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DestinationUi) {
            binding.titleText.text = item.title
            binding.countryText.text = item.country
            binding.tagsText.text = item.tagLine
            binding.previewImage.load(item.imageUrl) {
                crossfade(true)
            }
        }
    }

    private object DestinationDiff : DiffUtil.ItemCallback<DestinationUi>() {
        override fun areItemsTheSame(oldItem: DestinationUi, newItem: DestinationUi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DestinationUi, newItem: DestinationUi): Boolean {
            return oldItem == newItem
        }
    }
}

