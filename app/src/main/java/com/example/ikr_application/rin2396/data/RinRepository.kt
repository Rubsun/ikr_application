package com.example.ikr_application.rin2396.data

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.example.ikr_application.rin2396.data.models.RinInfo

class RinRepository {
    fun rinInfo(): RinInfo {
        return RinInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }

    companion object {
        @Discouraged("Rin")
        val INSTANCE = RinRepository()
    }
}