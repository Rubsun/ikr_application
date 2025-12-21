package com.kristevt.impl.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kristevt.api.domain.models.Song
import com.kristevt.impl.R

private const val TYPE_ADD = 0
private const val TYPE_SONG = 1

internal class SongAdapter(
    private val onLyricsClick: (Song) -> Unit,
    private val onAddSong: (Song) -> Unit,
    private val onDeleteSong: (Song) -> Unit
) : ListAdapter<Song, RecyclerView.ViewHolder>(Callback()) {

    override fun getItemCount(): Int = currentList.size + 1

    override fun getItemViewType(position: Int): Int =
        if (position == 0) TYPE_ADD else TYPE_SONG

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_ADD -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_kristevt_add_song, parent, false)
                AddSongViewHolder(view, onAddSong)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_kristevt_song, parent, false)
                SongViewHolder(view, onLyricsClick, onDeleteSong)
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SongViewHolder) {
            holder.bind(getItem(position - 1))
        }
    }

    private class Callback : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(old: Song, new: Song): Boolean =
            old.title == new.title && old.author == new.author

        override fun areContentsTheSame(old: Song, new: Song): Boolean =
            old == new
    }
}
