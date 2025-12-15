package com.example.ikr_application.egorik4.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ikr_application.R
import com.example.ikr_application.egorik4.ui.models.BookDisplayModel

class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val titleView: TextView by lazy { itemView.findViewById<TextView>(R.id.book_title) }
    private val authorView: TextView by lazy { itemView.findViewById<TextView>(R.id.book_author) }
    private val infoView: TextView by lazy { itemView.findViewById<TextView>(R.id.book_info) }

    fun bind(item: BookDisplayModel?) {
        when {
            item == null -> {
                titleView.text = null
                authorView.text = null
                infoView.text = null
            }
            else -> {
                titleView.text = itemView.context.getString(
                    R.string.egorik4_book_title_pattern,
                    item.displayTitle
                )
                authorView.text = itemView.context.getString(
                    R.string.egorik4_book_author_pattern,
                    item.displayAuthor
                )
                infoView.text = item.displayInfo
            }
        }
    }
}

