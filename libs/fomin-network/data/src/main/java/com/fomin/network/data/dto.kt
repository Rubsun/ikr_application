package com.fomin.network.data

import com.fomin.network.api.CatBreed
import com.fomin.network.api.CatImage
import com.fomin.network.api.CatWeight
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class BreedDto(
    val id: String = "",
    val name: String = "",
    val description: String? = null,
    val temperament: String? = null,
    val origin: String? = null,
    @SerialName("life_span") val lifeSpan: String? = null,
    val weight: WeightDto? = null,
    val image: ImageDto? = null,
)

@Serializable
internal data class WeightDto(
    val imperial: String? = null,
    val metric: String? = null,
)

@Serializable
internal data class ImageDto(
    val id: String = "",
    val url: String = "",
    val width: Int? = null,
    val height: Int? = null,
)

internal fun BreedDto.toDomain(): CatBreed {
    return CatBreed(
        id = id,
        name = name,
        description = description,
        temperament = temperament,
        origin = origin,
        lifeSpan = lifeSpan,
        weight = weight?.toDomain(),
        image = image?.toDomain(),
    )
}

internal fun WeightDto.toDomain(): CatWeight {
    return CatWeight(
        imperial = imperial,
        metric = metric,
    )
}

internal fun ImageDto.toDomain(): CatImage {
    return CatImage(
        id = id,
        url = url,
        width = width,
        height = height,
    )
}


