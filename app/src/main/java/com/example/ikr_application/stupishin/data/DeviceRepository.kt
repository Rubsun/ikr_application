package com.example.ikr_application.stupishin.data

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.example.ikr_application.stupishin.data.models.DeviceInfo

class DeviceRepository {
    fun deviceInfo(): DeviceInfo {
        return DeviceInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }

    companion object {
        @Discouraged("Anime List")
        val INSTANCE = DeviceRepository()
    }
}
