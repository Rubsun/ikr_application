package com.example.ikr_application.navigation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.ikr_application.R

class ScreensAdapter(
    private val listener: Listener,
) : ListAdapter<Screens, ScreenViewHolder>(ScreenItemCallback()) {
    interface Listener : ScreenViewHolder.ClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScreenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_screen, parent, false)
        val holder = ScreenViewHolder(view, listener)

        return holder
    }

    override fun onBindViewHolder(
        holder: ScreenViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    private class ScreenItemCallback : DiffUtil.ItemCallback<Screens>() {
        override fun areItemsTheSame(
            oldItem: Screens,
            newItem: Screens
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: Screens,
            newItem: Screens
        ): Boolean {
            return oldItem == newItem
        }
    }
}