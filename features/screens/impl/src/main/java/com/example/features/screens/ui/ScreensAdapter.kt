package com.example.features.screens.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.features.screens.R
import com.example.features.screens.domain.Screen

internal class ScreensAdapter(
    private val listener: Listener,
) : ListAdapter<Screen, ScreenViewHolder>(ScreenItemCallback()) {
    interface Listener : ScreenViewHolder.ClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScreenViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_screens_screen, parent, false)
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

    private class ScreenItemCallback : DiffUtil.ItemCallback<Screen>() {
        override fun areItemsTheSame(
            oldItem: Screen,
            newItem: Screen
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: Screen,
            newItem: Screen
        ): Boolean {
            return oldItem == newItem
        }
    }
}