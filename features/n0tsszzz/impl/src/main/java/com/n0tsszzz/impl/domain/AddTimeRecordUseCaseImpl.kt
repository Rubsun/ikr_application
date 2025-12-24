package com.n0tsszzz.impl.domain

import android.os.SystemClock
import com.n0tsszzz.api.domain.models.MarkoInfo
import com.n0tsszzz.api.domain.usecases.AddTimeRecordUseCase
import com.n0tsszzz.impl.data.MarkoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AddTimeRecordUseCaseImpl(
    private val repository: MarkoRepository
) : AddTimeRecordUseCase {
    override suspend fun invoke(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            // Получаем время из интернета через API (с fallback на локальное время при ошибке)
            val info = repository.getCurrentTimeFromApi()
            repository.addTimeRecord(info)
            Result.success(Unit)
        } catch (e: Exception) {
            // Если даже fallback не сработал, возвращаем ошибку
            Result.failure(e)
        }
    }
}

