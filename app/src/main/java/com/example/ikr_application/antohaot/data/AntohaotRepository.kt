package com.example.ikr_application.antohaot.data

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.example.ikr_application.antohaot.data.models.AntohaotInfo

class AntohaotRepository {
    fun antohaotInfo(): AntohaotInfo {
        return AntohaotInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }

    companion object {
        @Discouraged("Antohaot")
        val INSTANCE = AntohaotRepository()
    }
}