package com.tire.impl.data.models

import kotlinx.serialization.Serializable

@Serializable
internal data class DeviceInfoDto(
    val id: String = System.currentTimeMillis().toString(),
    val currentTime: Long,
    val elapsedTime: Long,
    val deviceName: String = "Device ${System.currentTimeMillis() % 1000}"
)
