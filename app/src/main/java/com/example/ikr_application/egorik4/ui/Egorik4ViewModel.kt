package com.example.ikr_application.egorik4.ui

import androidx.lifecycle.ViewModel
import com.example.ikr_application.egorik4.domain.SearchBooksUseCase
import com.example.ikr_application.egorik4.ui.models.BookDisplayModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
class Egorik4ViewModel : ViewModel() {
    private val searchBooksUseCase = SearchBooksUseCase()
    private val queryFlow = MutableStateFlow("")
    private val state = queryFlow
        .debounce(600.milliseconds)
        .map(searchBooksUseCase::invoke)
        .map { result ->
            State(
                data = result.getOrNull()?.map { book ->
                    BookDisplayModel(
                        displayTitle = book.title,
                        displayAuthor = book.author,
                        displayInfo = book.year?.toString() ?: "Unknown year",
                        coverImageUrl = book.coverImageUrl
                    )
                } ?: emptyList(),
                isLoading = false,
                error = result.exceptionOrNull()
            )
        }
        .distinctUntilChanged()

    fun state(): Flow<State> {
        return state
    }

    fun search(query: String) {
        queryFlow.value = query
    }

    data class State(
        val data: List<BookDisplayModel> = emptyList(),
        val isLoading: Boolean = false,
        val error: Throwable? = null,
    )
}

