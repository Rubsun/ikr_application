package com.kristevt.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.injector.inject
import com.kristevt.api.domain.GetLyricsUseCase
import com.kristevt.api.domain.SongsListUseCase
import com.kristevt.api.domain.models.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

internal class KristevtViewModel : ViewModel() {

    private val songsUseCase: SongsListUseCase by inject()
    private val lyricsUseCase: GetLyricsUseCase by inject()

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state

    fun loadSongs(sortAscending: Boolean) {
        viewModelScope.launch {
            songsUseCase(sortAscending).onSuccess {
                _state.value = _state.value.copy(songs = it)
            }
        }
    }

    fun getLyrics(song: Song) {
        viewModelScope.launch {
            lyricsUseCase(song.author, song.title)
                .onSuccess {
                    _state.value = _state.value.copy(lyrics = it)
                }
                .onFailure {
                    _state.value = _state.value.copy(error = it)
                }
        }
    }

    fun addSong(song: Song) {
        viewModelScope.launch {
            songsUseCase.addSong(song)
                .onSuccess { loadSongs(true) }
                .onFailure {
                    _state.value = _state.value.copy(error = it)
                }
        }
    }

    fun deleteSong(song: Song) {
        viewModelScope.launch {
            songsUseCase.deleteSong(song)
                .onSuccess { loadSongs(true) }
                .onFailure {
                    _state.value = _state.value.copy(error = it)
                }
        }
    }

    data class State(
        val songs: List<Song> = emptyList(),
        val lyrics: String = "",
        val error: Throwable? = null
    )
}
