package com.example.ikr_application.kristevt.domain

import com.example.ikr_application.kristevt.data.SongsRepository
import com.example.ikr_application.kristevt.data.models.SongDto
import com.example.ikr_application.kristevt.domain.models.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class SongsListUseCase(
    private val repository: SongsRepository = SongsRepository.INSTANCE
) {

    suspend operator fun invoke(sortAscending: Boolean): Result<List<Song>> =
        withContext(Dispatchers.IO) {
            runCatching {
                val songs = repository.getSongs()
                    .map(::mapSong)

                if (sortAscending)
                    songs.sortedBy { it.year }
                else
                    songs.sortedByDescending { it.year }
            }
        }

    private fun mapSong(dto: SongDto): Song {
        return Song(
            title = dto.title,
            author = dto.author,
            year = dto.year,
            genre = dto.genre
        )
    }
}
