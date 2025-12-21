package com.stupishin.impl.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.stupishin.api.domain.models.Anime
import com.stupishin.impl.databinding.ItemStupishinAnimeBinding

internal class StuAnimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val b = ItemStupishinAnimeBinding.bind(view)

    fun bind(item: Anime) {
        b.title.text = item.title
        b.poster.load(item.imageUrl)
    }
}
