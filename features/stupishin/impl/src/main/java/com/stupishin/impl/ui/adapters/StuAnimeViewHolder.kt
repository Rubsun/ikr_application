package com.stupishin.impl.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.imageloader.api.ImageLoader
import com.stupishin.api.domain.models.Anime
import com.stupishin.impl.databinding.ItemStupishinAnimeBinding

internal class StuAnimeViewHolder(
    view: View,
    private val imageLoader: ImageLoader,
) : RecyclerView.ViewHolder(view) {
    private val b = ItemStupishinAnimeBinding.bind(view)

    fun bind(item: Anime) {
        b.title.text = item.title
        val url = item.imageUrl
        if (url != null) {
            imageLoader.load(b.poster, url)
        } else {
            b.poster.setImageDrawable(null)
        }
    }
}
