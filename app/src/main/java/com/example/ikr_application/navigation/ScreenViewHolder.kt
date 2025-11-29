package com.example.ikr_application.navigation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ikr_application.R

class ScreenViewHolder(
    view: View,
    private val clickListener: ClickListener
) : RecyclerView.ViewHolder(view) {
    interface ClickListener {
        fun onClicked(item: Screens)
    }

    private val button by lazy { itemView.findViewById<TextView>(R.id.button) }

    fun bind(item: Screens?) {
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