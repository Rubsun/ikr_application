package com.example.ikr_application.kristevt.ui.adapters

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ikr_application.R
import com.example.ikr_application.kristevt.domain.models.Song

class SongViewHolder(
    view: View,
    private val onLyricsClick: (Song) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val title = view.findViewById<TextView>(R.id.title)
    private val info = view.findViewById<TextView>(R.id.info)
    private val button = view.findViewById<Button>(R.id.getLyrics)

    fun bind(song: Song) {
        title.text = song.title
        info.text = "${song.author} • ${song.year} • ${song.genre}"

        button.setOnClickListener {
            onLyricsClick(song)
        }
    }
}
