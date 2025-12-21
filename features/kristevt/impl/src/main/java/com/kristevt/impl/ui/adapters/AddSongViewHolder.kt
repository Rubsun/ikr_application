package com.kristevt.impl.ui.adapters

import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.kristevt.api.domain.models.Song
import com.kristevt.impl.R

internal class AddSongViewHolder(
    view: View,
    private val onAddSong: (Song) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val title = view.findViewById<EditText>(R.id.title)
    private val author = view.findViewById<EditText>(R.id.author)
    private val album = view.findViewById<EditText>(R.id.album)
    private val year = view.findViewById<EditText>(R.id.year)
    private val genre = view.findViewById<EditText>(R.id.genre)
    private val addButton = view.findViewById<Button>(R.id.addSong)

    init {
        addButton.setOnClickListener {
            val song = validateAndBuildSong() ?: return@setOnClickListener
            onAddSong(song)
            clearFields()
        }
    }

    private fun validateAndBuildSong(): Song? {
        val titleText = title.text.toString().trim()
        val authorText = author.text.toString().trim()
        val albumText = album.text.toString().trim()
        val yearText = year.text.toString().trim()
        val genreText = genre.text.toString().trim()

        if (
            titleText.isEmpty() ||
            authorText.isEmpty() ||
            albumText.isEmpty() ||
            yearText.isEmpty() ||
            genreText.isEmpty()
        ) {
            return null
        }

        val yearValue = yearText.toLongOrNull() ?: return null

        return Song(
            title = titleText,
            author = authorText,
            album = albumText,
            year = yearValue,
            genre = genreText
        )
    }

    private fun clearFields() {
        title.text?.clear()
        author.text?.clear()
        album.text?.clear()
        year.text?.clear()
        genre.text?.clear()
    }
}
