package com.example.ikr_application.drain678.domain

import android.os.SystemClock
import androidx.annotation.Discouraged
import com.example.ikr_application.drain678.data.Drain678Repository
import com.example.ikr_application.drain678.data.model.Drain678Info
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AddTimeRecordUseCase(
    @param:Discouraged("Drain678")
    private val repository: Drain678Repository = Drain678Repository.INSTANCE
) {
    suspend fun invoke(): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val info = Drain678Info(
                currentTime = System.currentTimeMillis(),
                elapsedTime = SystemClock.elapsedRealtime(),
            )
            repository.addTimeRecord(info)
        }
    }
}

