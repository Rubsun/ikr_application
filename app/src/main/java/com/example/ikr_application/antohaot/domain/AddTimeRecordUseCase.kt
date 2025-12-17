package com.example.ikr_application.antohaot.domain

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.example.ikr_application.antohaot.data.AntohaotRepository
import com.example.ikr_application.antohaot.data.models.AntohaotInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AddTimeRecordUseCase(
    @param:Discouraged("Antohaot")
    private val repository: AntohaotRepository = AntohaotRepository.INSTANCE
) {
    suspend fun invoke(): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val info = AntohaotInfo(
                currentTime = System.currentTimeMillis(),
                elapsedTime = SystemClock.elapsedRealtime(),
            )
            repository.addTimeRecord(info)
        }
    }
}

