package com.kristevt.impl.domain

import com.kristevt.api.domain.SongsListUseCase
import com.kristevt.impl.data.SongsRepository
import com.kristevt.impl.data.models.SongDto
import com.kristevt.api.domain.models.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class SongsListUseCaseImpl(
    private val repository: SongsRepository
) : SongsListUseCase {

    override suspend fun invoke(sortAscending: Boolean): Result<List<Song>> =
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

    override suspend fun addSong(song: Song): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                repository.addSong(
                    SongDto(
                        title = song.title,
                        author = song.author,
                        album = song.album,
                        year = song.year,
                        genre = song.genre
                    )
                )
            }
        }

    override suspend fun deleteSong(song: Song): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                repository.deleteSong(
                    SongDto(
                        song.title,
                        song.author,
                        song.album,
                        song.year,
                        song.genre
                    )
                )
            }
        }

    private fun mapSong(dto: SongDto) = Song(
        dto.title, dto.author, dto.album, dto.year, dto.genre
    )
}
