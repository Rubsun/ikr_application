package com.kristevt.impl.ui.adapters

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kristevt.impl.R
import com.kristevt.api.domain.models.Song

internal class SongViewHolder(
    view: View,
    private val onLyricsClick: (Song) -> Unit,
    private val onDeleteClick: (Song) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val title = view.findViewById<TextView>(R.id.title)
    private val info = view.findViewById<TextView>(R.id.info)
    private val lyricsBtn = view.findViewById<Button>(R.id.getLyrics)
    private val deleteBtn = view.findViewById<Button>(R.id.deleteSong)

    fun bind(song: Song) {
        title.text = song.title
        info.text = "${song.author} • ${song.year} • ${song.genre}"

        lyricsBtn.setOnClickListener { onLyricsClick(song) }
        deleteBtn.setOnClickListener { onDeleteClick(song) }
    }
}
