package com.n0tsszzz.network.api.models

import kotlinx.serialization.Serializable

@Serializable
data class TimeDto(
    val datetime: String,
    val unixtime: Long
)

