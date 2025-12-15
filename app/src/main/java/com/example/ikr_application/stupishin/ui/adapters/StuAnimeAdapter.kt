package com.example.ikr_application.stupishin.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.ikr_application.R
import com.example.ikr_application.stupishin.domain.models.Anime

internal class StuAnimeAdapter : ListAdapter<Anime, StuAnimeViewHolder>(Diff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StuAnimeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_stupishin_anime, parent, false)

        return StuAnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: StuAnimeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object Diff : DiffUtil.ItemCallback<Anime>() {
        override fun areItemsTheSame(oldItem: Anime, newItem: Anime) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Anime, newItem: Anime) = oldItem == newItem
    }
}
