package com.artemkaa.impl.domain

import android.os.SystemClock
import com.artemkaa.api.domain.models.ArtemkaaInfo
import com.artemkaa.api.domain.usecases.AddTimeRecordUseCase
import com.artemkaa.impl.data.ArtemkaaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AddTimeRecordUseCaseImpl(
    private val repository: ArtemkaaRepository
) : AddTimeRecordUseCase {
    override suspend fun invoke(): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val info = ArtemkaaInfo(
                currentTime = System.currentTimeMillis(),
                elapsedTime = SystemClock.elapsedRealtime(),
            )
            repository.addTimeRecord(info)
        }
    }
}

