package com.nfirex.impl.ui

import androidx.lifecycle.ViewModel
import com.example.injector.inject
import com.nfirex.api.domain.models.Emoji
import com.nfirex.api.domain.usecases.EmojiListUseCase
import com.nfirex.api.domain.usecases.EmojiSuggestUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
internal class EmojiViewModel : ViewModel() {
    private val emojiListUseCase: EmojiListUseCase by inject()
    private val emojiSuggestUseCase: EmojiSuggestUseCase by inject()

    private val queryFlow = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val state = queryFlow
        .filterNotNull()
        .debounce(600.milliseconds)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            flow {
                emit(
                    State(
                        query = query,
                        isPending = true
                    )
                )

                val result = emojiListUseCase.invoke(query)

                emit(
                    State(
                        query = result.query,
                        data = result.items,
                        error = result.error,
                        isPending = false,
                    )
                )
            }
        }
        .onStart {
            val initialQuery = emojiSuggestUseCase.invoke()
                ?: ""

            emit(
                State(
                    query = initialQuery,
                    isPending = false,
                )
            )
        }

    fun state(): Flow<State> {
        return state
    }

    fun search(query: String) {
        queryFlow.value = query
    }

    data class State(
        val query: String,
        val isPending: Boolean,
        val data: List<Emoji> = emptyList(),
        val error: Throwable? = null,
    )
}