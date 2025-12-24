package com.antohaot.network.api.models

import kotlinx.serialization.Serializable

@Serializable
data class TimeDto(
    val datetime: String,
    val timezone: String,
    val unixtime: Long
)

