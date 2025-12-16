package com.example.ikr_application.nfirex.data

import androidx.annotation.Discouraged
import com.example.ikr_application.nfirex.data.models.DeviceInfo

@Deprecated("Use your own classes:)")
class DeviceRepository {
    init {
        throw IllegalStateException("Removed class")
    }

    fun deviceInfo(): DeviceInfo {
        throw IllegalStateException("Removed class")
    }

    companion object {
        @Discouraged("Only for example")
        val INSTANCE = DeviceRepository()
    }
}