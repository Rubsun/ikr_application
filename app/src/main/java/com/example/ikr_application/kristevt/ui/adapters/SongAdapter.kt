package com.example.ikr_application.kristevt.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.ikr_application.R
import com.example.ikr_application.kristevt.domain.models.Song

internal class SongAdapter(
    private val onLyricsClick: (Song) -> Unit
) : ListAdapter<Song, SongViewHolder>(Callback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kristevt_song, parent, false)
        return SongViewHolder(view, onLyricsClick)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class Callback : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(old: Song, new: Song) =
            old.title == new.title && old.author == new.author

        override fun areContentsTheSame(old: Song, new: Song) = old == new
    }
}
