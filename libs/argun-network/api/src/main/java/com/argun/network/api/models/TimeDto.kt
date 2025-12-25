package com.argun.network.api.models

import kotlinx.serialization.Serializable

// временная модель для обратной совместимости
@Serializable
data class TimeDto(
    val datetime: String,
    val timezone: String,
    val unixtime: Long
)

