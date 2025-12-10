package com.example.ikr_application.rin2396.data

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.example.ikr_application.rin2396.data.models.DeviceInfo

class DeviceRepository {
    fun deviceInfo(): DeviceInfo {
        return DeviceInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }

    companion object {
        @Discouraged("lalala")
        val INSTANCE = DeviceRepository()
    }
}