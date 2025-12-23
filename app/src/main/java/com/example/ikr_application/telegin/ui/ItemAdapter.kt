package com.example.ikr_application.telegin.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.example.ikr_application.R
import com.example.ikr_application.telegin.domain.Item

class ItemAdapter :
    ListAdapter<Item, ItemAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
    }

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val icon: ImageView = view.findViewById(R.id.iconImageView)
        private val title: TextView = view.findViewById(R.id.titleTextView)
        private val desc: TextView = view.findViewById(R.id.descriptionTextView)

        fun bind(item: Item) {
            title.text = item.title
            desc.text = item.description

            icon.load("https://picsum.photos/seed/${item.id}/200/200")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_telegin,
            parent,
            false
        )
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}
