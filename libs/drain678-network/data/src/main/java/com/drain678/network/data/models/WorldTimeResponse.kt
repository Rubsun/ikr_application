package com.drain678.network.data.models

import kotlinx.serialization.Serializable

/**
 * Ответ от World Time API.
 * Внутренняя модель для работы с Retrofit, не должна использоваться вне data модуля.
 */
@Serializable
internal data class WorldTimeResponse(
    val datetime: String,
    val timezone: String,
    val unixtime: Long
)

