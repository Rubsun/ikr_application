package com.example.ikr_application.rin2396.domain

import android.os.SystemClock
import com.example.ikr_application.rin2396.data.RinRepository
import com.example.ikr_application.rin2396.data.models.RinInfo

class RinAddTimeEntryUseCase {
    suspend fun execute() {
        val info = RinInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
        RinRepository.INSTANCE.addTimeEntry(info)
    }
}

