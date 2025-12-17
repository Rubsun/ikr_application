package com.example.ikr_application.n0tsSzzz.domain

import android.annotation.SuppressLint
import android.os.SystemClock
import com.example.ikr_application.n0tsSzzz.data.MarkoRepository
import com.example.ikr_application.n0tsSzzz.data.models.MarkoInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@SuppressLint("DiscouragedApi")
internal class AddTimeRecordUseCase(
    @param:SuppressLint("DiscouragedApi")
    private val repository: MarkoRepository = MarkoRepository.INSTANCE
) {
    suspend fun invoke(): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val info = MarkoInfo(
                currentTime = System.currentTimeMillis(),
                elapsedTime = SystemClock.elapsedRealtime(),
            )
            repository.addTimeRecord(info)
        }
    }
}
