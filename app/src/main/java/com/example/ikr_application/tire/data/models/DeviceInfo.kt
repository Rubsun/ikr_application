package com.example.ikr_application.tire.data.models

data class DeviceInfo(
    val id: String = System.currentTimeMillis().toString(),
    val currentTime: Long,
    val elapsedTime: Long,
    val deviceName: String = "Device ${System.currentTimeMillis() % 1000}"
)

