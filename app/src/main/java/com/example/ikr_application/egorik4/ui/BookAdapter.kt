package com.example.ikr_application.egorik4.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.ikr_application.R
import com.example.ikr_application.egorik4.ui.models.BookDisplayModel

class BookAdapter : ListAdapter<BookDisplayModel, BookViewHolder>(BookDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_egorik4_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class BookDiff : DiffUtil.ItemCallback<BookDisplayModel>() {
        override fun areItemsTheSame(
            oldItem: BookDisplayModel,
            newItem: BookDisplayModel
        ): Boolean = oldItem.displayTitle == newItem.displayTitle

        override fun areContentsTheSame(
            oldItem: BookDisplayModel,
            newItem: BookDisplayModel
        ): Boolean = oldItem == newItem
    }
}

