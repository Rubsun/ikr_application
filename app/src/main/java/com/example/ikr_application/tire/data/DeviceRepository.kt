package com.example.ikr_application.tire.data

import android.os.SystemClock
import com.example.ikr_application.tire.data.models.DeviceInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DeviceRepository {
    private val _devices = MutableStateFlow<List<DeviceInfo>>(emptyList())
    val devices: Flow<List<DeviceInfo>> = _devices.asStateFlow()

    suspend fun deviceInfo(): DeviceInfo {
        delay(100)
        return DeviceInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }

    suspend fun addDevice(info: DeviceInfo) {
        delay(50)
        _devices.value += info
    }

    suspend fun getAllDevices(filter: String = ""): List<DeviceInfo> {
        delay(50)
        return if (filter.isEmpty()) {
            _devices.value
        } else {
            _devices.value.filter {
                it.deviceName.contains(filter, ignoreCase = true)
            }
        }
    }

    companion object {
        val INSTANCE = DeviceRepository()
    }
}

