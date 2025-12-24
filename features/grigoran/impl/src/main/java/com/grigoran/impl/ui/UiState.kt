package com.grigoran.impl.ui


data class UiState(
    var isLoading: Boolean = false,
    var items: List<ItemUi> = emptyList(),
    var error: String? = null

)

data class ItemUi(
    var id: Int,
    val displayTitle: String,
    var displayPrice: String,
    val imageUrt: String
)



