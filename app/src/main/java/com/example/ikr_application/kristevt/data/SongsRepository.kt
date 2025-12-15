package com.example.ikr_application.kristevt.data

import com.example.ikr_application.kristevt.data.models.SongDto

class SongsRepository {
    fun getSongs(): List<SongDto> {
        return listOf(
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
    }

    companion object {
        val INSTANCE = SongsRepository()
    }
}
