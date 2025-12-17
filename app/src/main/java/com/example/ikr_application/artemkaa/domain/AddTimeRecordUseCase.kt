package com.example.ikr_application.artemkaa.domain

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.example.ikr_application.artemkaa.data.ArtemkaaRepository
import com.example.ikr_application.artemkaa.data.models.ArtemkaaInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AddTimeRecordUseCase(
    @param:Discouraged("Artemkaa")
    private val repository: ArtemkaaRepository = ArtemkaaRepository.INSTANCE
) {
    suspend fun invoke(): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val info = ArtemkaaInfo(
                currentTime = System.currentTimeMillis(),
                elapsedTime = SystemClock.elapsedRealtime(),
            )
            repository.addTimeRecord(info)
        }
    }
}

