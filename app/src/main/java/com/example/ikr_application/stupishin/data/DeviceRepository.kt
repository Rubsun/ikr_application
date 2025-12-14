package com.example.ikr_application.stupishin.data

import android.os.SystemClock
import com.example.ikr_application.stupishin.data.models.DeviceInfo

class DeviceRepository {
    fun deviceInfo(): DeviceInfo {
        return DeviceInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }
}
