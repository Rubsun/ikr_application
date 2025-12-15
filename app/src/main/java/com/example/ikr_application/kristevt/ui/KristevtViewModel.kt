package com.example.ikr_application.kristevt.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ikr_application.kristevt.domain.GetLyricsUseCase
import com.example.ikr_application.kristevt.domain.SongsListUseCase
import com.example.ikr_application.kristevt.domain.models.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class KristevtViewModel : ViewModel() {

    private val songsUseCase = SongsListUseCase()
    private val lyricsUseCase = GetLyricsUseCase()

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

    data class State(
        val songs: List<Song> = emptyList(),
        val lyrics: String = "",
        val error: Throwable? = null
    )
}
