package com.example.ikr_application.grigoran.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ikr_application.databinding.ItemGrigoranBinding

class ExampleAdapter : ListAdapter<ItemUi, ExampleAdapter.VH>(Diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemGrigoranBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VH(private val b: ItemGrigoranBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: ItemUi) {
            b.title.text = item.displayTitle
            b.price.text = item.displayPrice
        }
    }

    object Diff : DiffUtil.ItemCallback<ItemUi>() {
        override fun areItemsTheSame(oldItem: ItemUi, newItem: ItemUi) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ItemUi, newItem: ItemUi) = oldItem == newItem
    }
}