package com.kristevt.impl.data

import android.content.Context
import com.kristevt.impl.data.models.SongDto
import kotlinx.serialization.json.Json
import java.io.File

internal class SongsRepository(
    private val context: Context
) {
    private val json = Json { prettyPrint = true }
    private val file by lazy {
        File(context.filesDir, FILE_NAME)
    }

    fun getSongs(): List<SongDto> {
        if (!file.exists()) {
            saveSongs(defaultSongs())
        }

        return runCatching {
            json.decodeFromString<List<SongDto>>(file.readText())
        }.getOrDefault(emptyList())
    }

    fun addSong(song: SongDto) {
        val updated = getSongs() + song
        saveSongs(updated)
    }

    private fun saveSongs(songs: List<SongDto>) {
        file.writeText(json.encodeToString(songs))
    }

    fun deleteSong(song: SongDto) {
        val updated = getSongs().filterNot {
            it.title == song.title && it.author == song.author
        }
        saveSongs(updated)
    }

    private fun defaultSongs() = listOf(
            SongDto(
                title = "Master of Puppets",
                author = "Metallica",
                album = "Master of Puppets",
                year = 1986,
                genre = "Thrash Metal"
            ),
            SongDto(
                title = "Painkiller",
                author = "Judas Priest",
                album = "Painkiller",
                year = 1990,
                genre = "Heavy Metal"
            ),
            SongDto(
                title = "Paranoid",
                author = "Black Sabbath",
                album = "Paranoid",
                year = 1970,
                genre = "Heavy Metal"
            ),
            SongDto(
                title = "Raining Blood",
                author = "Slayer",
                album = "Reign in Blood",
                year = 1986,
                genre = "Thrash Metal"
            ),
            SongDto(
                title = "The Number of the Beast",
                author = "Iron Maiden",
                album = "The Number of the Beast",
                year = 1982,
                genre = "Heavy Metal"
            ),
            SongDto(
                title = "Ace of Spades",
                author = "Motörhead",
                album = "Ace of Spades",
                year = 1980,
                genre = "Speed Metal"
            ),
            SongDto(
                title = "Holy Wars... The Punishment Due",
                author = "Megadeth",
                album = "Rust in Peace",
                year = 1990,
                genre = "Thrash Metal"
            ),
            SongDto(
                title = "Hammer Smashed Face",
                author = "Cannibal Corpse",
                album = "Tomb of the Mutilated",
                year = 1992,
                genre = "Death Metal"
            ),
            SongDto(
                title = "Du Hast",
                author = "Rammstein",
                album = "Sehnsucht",
                year = 1997,
                genre = "Industrial Metal"
            ),
            SongDto(
                title = "Freezing Moon",
                author = "Mayhem",
                album = "De Mysteriis Dom Sathanas",
                year = 1994,
                genre = "Black Metal"
            ),
        )

    companion object {
        private const val FILE_NAME = "kristevt_songs.json"
    }
}
