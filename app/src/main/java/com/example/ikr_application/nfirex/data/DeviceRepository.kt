package com.example.ikr_application.nfirex.data

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.example.ikr_application.nfirex.data.models.DeviceInfo

@Deprecated("Use your own classes:)")
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