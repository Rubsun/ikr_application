package com.tire.api.domain.models

data class DeviceInfo(
    val id: String,
    val currentTime: Long,
    val elapsedTime: Long,
    val deviceName: String,
)
