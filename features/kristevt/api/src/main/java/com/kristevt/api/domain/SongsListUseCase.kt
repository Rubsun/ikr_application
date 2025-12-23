package com.kristevt.api.domain

import com.kristevt.api.domain.models.Song

interface SongsListUseCase {
    suspend operator fun invoke(sortAscending: Boolean): Result<List<Song>>
    suspend fun addSong(song: Song): Result<Unit>
    suspend fun deleteSong(song: Song): Result<Unit>
}

