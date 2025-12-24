package com.grigoran.network.data

import kotlinx.serialization.Serializable

@Serializable
internal data class ItemDto(val id: Int, val title: String, val price: Double, val thumbnail: String)

@Serializable
internal data class ItemResponseDto(
    val products: List<ItemDto>
)
