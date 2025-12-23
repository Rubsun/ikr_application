package com.n0tsszzz.api.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class MarkoInfo(
    val currentTime: Long,
    val elapsedTime: Long,
)

