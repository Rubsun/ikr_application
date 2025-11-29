package com.example.ikr_application.navigation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ikr_application.R

class ScreenViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    private val title by lazy { itemView.findViewById<TextView>(R.id.button) }

    private var item: Screens? = null

    fun bind(item: Screens?) {
        this.item = item

        when {
            item == null -> title.text = null
            else -> title.setText(item.title)
        }
    }
}