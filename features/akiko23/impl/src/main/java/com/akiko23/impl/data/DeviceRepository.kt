package com.akiko23.impl.data

import android.os.SystemClock
import com.akiko23.impl.data.models.DeviceInfo

/**
 * Репозиторий для получения информации об устройстве.
 */
internal class DeviceRepository {
    fun deviceInfo(): DeviceInfo {
        return DeviceInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }
}

