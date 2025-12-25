package com.alexcode69.impl.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexcode69.api.domain.models.TimeEntry
import com.alexcode69.impl.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal class TimeEntryAdapter : ListAdapter<TimeEntry, TimeEntryAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alexcode69_time_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val labelText: TextView = itemView.findViewById(R.id.entry_label)
        private val timeText: TextView = itemView.findViewById(R.id.entry_time)
        private val statusText: TextView = itemView.findViewById(R.id.entry_status)

        private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())

        fun bind(entry: TimeEntry) {
            labelText.text = entry.label
            timeText.text = "Time: ${dateFormat.format(Date(entry.timestamp))}"
            statusText.text = if (entry.isActive) "Active" else "Inactive"
            statusText.setTextColor(
                if (entry.isActive) {
                    itemView.context.getColor(android.R.color.holo_green_dark)
                } else {
                    itemView.context.getColor(android.R.color.darker_gray)
                }
            )
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<TimeEntry>() {
        override fun areItemsTheSame(oldItem: TimeEntry, newItem: TimeEntry): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TimeEntry, newItem: TimeEntry): Boolean {
            return oldItem == newItem
        }
    }
}

