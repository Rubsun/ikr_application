package com.example.ikr_application.nfirex.data.models

@Deprecated("Use your own classes:)")
class DeviceInfo(
    val currentTime: Long,
    val elapsedTime: Long,
) {
    init {
        throw IllegalStateException("Removed class")
    }
}