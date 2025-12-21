package com.example.ikr_application.michaelnoskov.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ikr_application.R
import com.example.ikr_application.michaelnoskov.data.models.FilteredItem
import java.text.SimpleDateFormat
import java.util.Locale

class ItemsAdapter : ListAdapter<FilteredItem, ItemsAdapter.ItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_element, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(R.id.item_text)
        private val timeView: TextView = view.findViewById(R.id.item_time)

        fun bind(item: FilteredItem) {
            textView.text = item.text
            val formattedTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                .format(item.timestamp)
            timeView.text = formattedTime
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FilteredItem>() {
        override fun areItemsTheSame(oldItem: FilteredItem, newItem: FilteredItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FilteredItem, newItem: FilteredItem): Boolean {
            return oldItem == newItem
        }
    }
}