package com.example.ikr_application.nfirex.ui

import androidx.lifecycle.ViewModel
import com.example.ikr_application.nfirex.domain.EmojiListUseCase
import com.example.ikr_application.nfirex.domain.models.Emoji
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
class EmojiViewModel : ViewModel() {
    private val emojiListUseCase = EmojiListUseCase()
    private val queryFlow = MutableStateFlow("")
    private val state = queryFlow
        .debounce(600.milliseconds)
        .map(emojiListUseCase::invoke)
        .map { State(
            data = it.getOrNull()?: emptyList(),
            error = it.exceptionOrNull())
        }
        .distinctUntilChanged()

    fun state(): Flow<State> {
        return state
    }

    fun search(query: String) {
        queryFlow.value = query
    }

    data class State(
        val data: List<Emoji> = emptyList(),
        val error: Throwable? = null,
    )
}