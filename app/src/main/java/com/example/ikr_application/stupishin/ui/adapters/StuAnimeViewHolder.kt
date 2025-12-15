package com.example.ikr_application.stupishin.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.example.ikr_application.databinding.ItemStupishinAnimeBinding
import com.example.ikr_application.stupishin.domain.models.Anime

class StuAnimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val b = ItemStupishinAnimeBinding.bind(view)

    fun bind(item: Anime) {
        b.title.text = item.title
        b.poster.load(item.imageUrl)
    }
}
