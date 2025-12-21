package com.antohaot.impl.domain

import android.os.SystemClock
import com.antohaot.api.domain.models.AntohaotInfo
import com.antohaot.api.domain.usecases.AddTimeRecordUseCase
import com.antohaot.impl.data.AntohaotRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AddTimeRecordUseCaseImpl(
    private val repository: AntohaotRepository
) : AddTimeRecordUseCase {
    override suspend fun invoke(): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val info = AntohaotInfo(
                currentTime = System.currentTimeMillis(),
                elapsedTime = SystemClock.elapsedRealtime(),
            )
            repository.addTimeRecord(info)
        }
    }
}

