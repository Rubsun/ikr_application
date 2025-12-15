package com.example.ikr_application.tire.data

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.example.ikr_application.tire.data.models.DeviceInfo

class DeviceRepository {
    fun deviceInfo(): DeviceInfo {
        return DeviceInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }

    companion object {
        @Discouraged("Only for example")
        val INSTANCE = DeviceRepository()
    }
}

