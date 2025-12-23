package com.tire.impl.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.tire.impl.data.models.DeviceInfoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.core.content.edit

internal class DeviceStorage(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    suspend fun saveDevices(devices: List<DeviceInfoDto>) = withContext(Dispatchers.IO) {
        val devicesJson = devices.joinToString(SEPARATOR) { device ->
            "${device.id}$FIELD_SEP${device.currentTime}$FIELD_SEP${device.elapsedTime}$FIELD_SEP${device.deviceName}"
        }
        prefs.edit { putString(KEY_DEVICES, devicesJson) }
    }

    suspend fun loadDevices(): List<DeviceInfoDto> = withContext(Dispatchers.IO) {
        val devicesJson = prefs.getString(KEY_DEVICES, "") ?: ""
        if (devicesJson.isEmpty()) {
            emptyList()
        } else {
            devicesJson.split(SEPARATOR).mapNotNull { line ->
                val parts = line.split(FIELD_SEP)
                if (parts.size == 4) {
                    DeviceInfoDto(
                        id = parts[0],
                        currentTime = parts[1].toLongOrNull() ?: 0L,
                        elapsedTime = parts[2].toLongOrNull() ?: 0L,
                        deviceName = parts[3]
                    )
                } else null
            }
        }
    }

    suspend fun saveLastPrecision(precision: String) = withContext(Dispatchers.IO) {
        prefs.edit { putString(KEY_LAST_PRECISION, precision) }
    }

    suspend fun loadLastPrecision(): String? = withContext(Dispatchers.IO) {
        prefs.getString(KEY_LAST_PRECISION, null)
    }

    companion object {
        private const val PREFS_NAME = "tire_prefs"
        private const val KEY_DEVICES = "devices"
        private const val KEY_LAST_PRECISION = "last_precision"
        private const val SEPARATOR = "|"
        private const val FIELD_SEP = ":"
    }
}
