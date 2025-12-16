package com.example.ikr_application.artemkaa.data

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.example.ikr_application.artemkaa.data.models.ArtemkaaInfo

class ArtemkaaRepository {
    fun artemkaaInfo(): ArtemkaaInfo {
        return ArtemkaaInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }

    companion object {
        @Discouraged("Artemkaa")
        val INSTANCE = ArtemkaaRepository()
    }
}