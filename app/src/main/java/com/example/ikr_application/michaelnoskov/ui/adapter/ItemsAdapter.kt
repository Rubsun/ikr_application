package com.example.ikr_application.michaelnoskov.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ikr_application.R
import com.example.ikr_application.michaelnoskov.domain.model.FilteredItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ItemsAdapter : ListAdapter<FilteredItem, ItemsAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_filtered_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.item_text)
        private val timestampView: TextView = itemView.findViewById(R.id.item_timestamp)

        fun bind(item: FilteredItem) {
            textView.text = item.text
            timestampView.text = formatTimestamp(item.timestamp)

            itemView.visibility = if (item.isVisible) View.VISIBLE else View.GONE
        }

        private fun formatTimestamp(timestamp: Long): String {
            return SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                .format(Date(timestamp))
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