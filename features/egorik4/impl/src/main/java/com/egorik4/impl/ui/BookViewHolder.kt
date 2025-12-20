package com.egorik4.impl.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.egorik4.api.ui.models.BookDisplayModel
import com.egorik4.impl.R

internal class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val coverView: ImageView by lazy { itemView.findViewById<ImageView>(R.id.book_cover) }
    private val titleView: TextView by lazy { itemView.findViewById<TextView>(R.id.book_title) }
    private val authorView: TextView by lazy { itemView.findViewById<TextView>(R.id.book_author) }
    private val infoView: TextView by lazy { itemView.findViewById<TextView>(R.id.book_info) }

    fun bind(item: BookDisplayModel?) {
        when {
            item == null -> {
                coverView.setImageDrawable(null)
                titleView.text = null
                authorView.text = null
                infoView.text = null
            }
            else -> {
                if (item.coverImageUrl != null) {
                    coverView.load(item.coverImageUrl)
                } else {
                    coverView.setImageDrawable(null)
                }
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
