package com.argun.impl.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.argun.api.domain.models.Zadacha
import com.argun.impl.R

internal class ZadachaAdapter : ListAdapter<Zadacha, ZadachaAdapter.ZadachaViewHolder>(ZadachaCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ZadachaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_argun_zadacha, parent, false)

        return ZadachaViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ZadachaViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ZadachaViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.zadacha_title)
        private val idTextView: TextView = itemView.findViewById(R.id.zadacha_id)
        private val completedTextView: TextView = itemView.findViewById(R.id.zadacha_completed)

        fun bind(zadacha: Zadacha) {
            titleTextView.text = zadacha.title
            idTextView.text = "ID: ${zadacha.id ?: "N/A"} | User ID: ${zadacha.userId}"
            completedTextView.text = "Status: ${if (zadacha.completed) "Completed" else "Not completed"}"
        }
    }

    private class ZadachaCallback : DiffUtil.ItemCallback<Zadacha>() {
        override fun areItemsTheSame(
            oldItem: Zadacha,
            newItem: Zadacha
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Zadacha,
            newItem: Zadacha
        ): Boolean {
            return oldItem == newItem
        }
    }
}

