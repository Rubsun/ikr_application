package com.example.ikr_application.dyatlova.ui

data class DyatlovaUiState(
    val destinations: List<DestinationUi> = emptyList(),
    val query: String = "",
    val draft: DestinationDraft = DestinationDraft(),
    val isEmpty: Boolean = true,
)

data class DestinationUi(
    val id: String,
    val title: String,
    val country: String,
    val imageUrl: String,
    val tagLine: String,
)

data class DestinationDraft(
    val title: String = "",
    val country: String = "",
    val imageUrl: String = "",
)

