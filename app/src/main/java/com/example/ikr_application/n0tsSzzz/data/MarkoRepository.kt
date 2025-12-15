package com.example.ikr_application.n0tsSzzz.data

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.example.ikr_application.n0tsSzzz.data.models.MarkoInfo

class MarkoRepository {
    fun deviceInfo(): MarkoInfo {
        return MarkoInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }

    companion object {
        @Discouraged("Only for marko")
        val INSTANCE = MarkoRepository()
    }
}

