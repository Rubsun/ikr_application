package com.tire.impl.data

import android.os.SystemClock
import com.tire.impl.data.models.DeviceInfoDto
import com.tire.impl.data.storage.DeviceStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class DeviceRepository(
    private val storage: DeviceStorage
) {
    private val _devices = MutableStateFlow<List<DeviceInfoDto>>(emptyList())
    val devices: Flow<List<DeviceInfoDto>> = _devices.asStateFlow()

    suspend fun initialize() {
        _devices.value = storage.loadDevices()
    }

    suspend fun deviceInfo(): DeviceInfoDto {
        delay(100)
        return DeviceInfoDto(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime()
        )
    }

    suspend fun addDevice(info: DeviceInfoDto) {
        delay(50)
        val newDevices = _devices.value + info
        _devices.value = newDevices
        storage.saveDevices(newDevices)
    }

    suspend fun getAllDevices(filter: String = ""): List<DeviceInfoDto> {
        delay(50)
        return if (filter.isEmpty()) {
            _devices.value
        } else {
            _devices.value.filter {
                it.deviceName.contains(filter, ignoreCase = true)
            }
        }
    }

    suspend fun saveLastPrecision(precision: String) {
        storage.saveLastPrecision(precision)
    }

    suspend fun loadLastPrecision(): String? {
        return storage.loadLastPrecision()
    }
}
