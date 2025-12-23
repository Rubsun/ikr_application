package com.example.jikan

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AnimeListResponseDto(
    val data: List<AnimeDto> = emptyList(),
)

@Serializable
internal data class AnimeDto(
    @SerialName("mal_id") val id: Int = 0,
    val title: String = "",
    @SerialName("title_english") val titleEnglish: String? = null,
    val images: AnimeImagesDto? = null,
)

@Serializable
internal data class AnimeImagesDto(
    val jpg: AnimeJpgDto? = null,
)

@Serializable
internal data class AnimeJpgDto(
    @SerialName("image_url") val imageUrl: String? = null,
)
