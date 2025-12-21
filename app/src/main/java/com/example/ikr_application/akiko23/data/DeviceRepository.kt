package com.example.ikr_application.akiko23.data

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.example.ikr_application.akiko23.data.models.DeviceInfo

/**
 * Репозиторий конкретно для фичи akiko23.
 * Не путать с учебным примером в пакете nfirex.
 */
class Akiko23DeviceRepository {
    fun deviceInfo(): DeviceInfo {
        return DeviceInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }

    companion object {
        @Discouraged("Only for example")
        val INSTANCE = Akiko23DeviceRepository()
    }
}
