package com.example.features.screens.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.features.screens.R
import com.example.features.screens.domain.Screen

internal class ScreenViewHolder(
    view: View,
    private val clickListener: ClickListener
) : RecyclerView.ViewHolder(view) {
    interface ClickListener {
        fun onClicked(item: Screen)
    }

    private val button by lazy { itemView.findViewById<TextView>(R.id.button) }

    fun bind(item: Screen?) {
        when {
            item == null -> button.apply {
                text = null
                setOnClickListener(null)
            }

            else -> button.apply {
                setText(item.title)
                setOnClickListener { clickListener.onClicked(item) }
            }
        }
    }
}