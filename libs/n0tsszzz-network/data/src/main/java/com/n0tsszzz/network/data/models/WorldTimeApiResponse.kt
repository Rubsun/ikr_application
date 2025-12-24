package com.n0tsszzz.network.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO для ответа HTTP World Clock API.
 * Внутренняя модель, не должна использоваться вне data модуля.
 */
@Serializable
internal data class WorldTimeApiResponse(
    @SerialName("currentDateTime")
    val currentDateTime: String,
    @SerialName("currentFileTime")
    val currentFileTime: Long? = null
)

