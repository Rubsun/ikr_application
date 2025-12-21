package com.dyatlova.impl.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dyatlova.api.domain.models.Destination
import com.dyatlova.api.domain.usecases.AddDestinationUseCase
import com.dyatlova.api.domain.usecases.FilterDestinationsUseCase
import com.dyatlova.api.domain.usecases.ObserveDestinationsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID

internal class DyatlovaViewModel(
    observeDestinationsUseCase: ObserveDestinationsUseCase,
    private val addDestinationUseCase: AddDestinationUseCase,
    private val filterDestinationsUseCase: FilterDestinationsUseCase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val query = MutableStateFlow("")
    private val draft = MutableStateFlow(DestinationDraft())
    private val destinationsFlow = observeDestinationsUseCase()

    val state: StateFlow<DyatlovaUiState> = combine(
        destinationsFlow,
        query,
        draft
    ) { destinations, query, draft ->
        val filtered = filterDestinationsUseCase(destinations, query)
        DyatlovaUiState(
            destinations = filtered.map { it.toUi() },
            query = query,
            draft = draft,
            isEmpty = filtered.isEmpty()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DyatlovaUiState()
    )

    fun onQueryChanged(value: String) {
        query.value = value
    }

    fun onTitleChanged(value: String) {
        draft.update { it.copy(title = value) }
    }

    fun onCountryChanged(value: String) {
        draft.update { it.copy(country = value) }
    }

    fun onImageChanged(value: String) {
        draft.update { it.copy(imageUrl = value) }
    }

    fun onAddDestination() {
        val currentDraft = draft.value
        val title = currentDraft.title.trim()
        val country = currentDraft.country.trim()
        if (title.isEmpty() || country.isEmpty()) return

        val destination = Destination(
            id = UUID.randomUUID().toString(),
            title = title,
            country = country,
            imageUrl = currentDraft.imageUrl.ifBlank { FALLBACK_IMAGE },
            tags = buildTags(title, country),
        )

        viewModelScope.launch(ioDispatcher) {
            addDestinationUseCase(destination)
            draft.value = DestinationDraft()
        }
    }

    private fun Destination.toUi(): DestinationUi = DestinationUi(
        id = id,
        title = title,
        country = country,
        imageUrl = imageUrl.ifBlank { FALLBACK_IMAGE },
        tagLine = tags.takeIf { it.isNotEmpty() }
            ?.joinToString(" • ") { tag -> tag.replaceFirstChar { char -> char.titlecase(Locale.getDefault()) } }
            ?: "",
    )

    private fun buildTags(title: String, country: String): List<String> = listOf(
        country,
        title,
        "favorite",
    ).filter { it.isNotBlank() }

    companion object {
        private const val FALLBACK_IMAGE =
            "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=800&q=80"
    }
}



