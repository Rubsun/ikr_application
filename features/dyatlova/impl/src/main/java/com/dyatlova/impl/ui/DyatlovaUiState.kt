package com.dyatlova.impl.ui

internal data class DestinationDraft(
    val title: String = "",
    val country: String = "",
    val imageUrl: String = "",
)

internal data class DestinationUi(
    val id: String,
    val title: String,
    val country: String,
    val imageUrl: String,
    val tagLine: String,
)

internal data class DyatlovaUiState(
    val destinations: List<DestinationUi> = emptyList(),
    val query: String = "",
    val draft: DestinationDraft = DestinationDraft(),
    val isEmpty: Boolean = false,
)



