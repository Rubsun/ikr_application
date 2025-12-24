package com.antohaot.impl.data

import android.content.Context
import android.content.SharedPreferences
import android.os.SystemClock
import com.antohaot.api.domain.models.AntohaotInfo
import com.antohaot.network.api.TimeApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class AntohaotInfoSerializable(
    val currentTime: Long,
    val elapsedTime: Long,
) {
    fun toAntohaotInfo(): AntohaotInfo {
        return AntohaotInfo(
            currentTime = currentTime,
            elapsedTime = elapsedTime
        )
    }
}

fun AntohaotInfo.toSerializable(): AntohaotInfoSerializable {
    return AntohaotInfoSerializable(
        currentTime = currentTime,
        elapsedTime = elapsedTime
    )
}

internal class AntohaotRepository(
    private val context: Context,
    private val timeApiClient: TimeApiClient
) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "antohaot_prefs",
        Context.MODE_PRIVATE
    )
    private val json = Json { ignoreUnknownKeys = true }
    private val recordsKey = "time_records"

    private val _timeRecords = MutableStateFlow<List<AntohaotInfo>>(loadRecords())
    val timeRecords: StateFlow<List<AntohaotInfo>> = _timeRecords.asStateFlow()

    init {
        // Загружаем сохраненные записи при инициализации
        _timeRecords.value = loadRecords()
    }

    /**
     * Получает информацию о времени из интернета с fallback на локальное время при ошибке.
     */
    suspend fun antohaotInfo(): AntohaotInfo = withContext(Dispatchers.IO) {
        try {
            // Пытаемся получить время из интернета
            val timeDto = timeApiClient.getCurrentTime("UTC")
            AntohaotInfo(
                currentTime = timeDto.unixtime * 1000, // Конвертируем секунды в миллисекунды
                elapsedTime = SystemClock.elapsedRealtime(),
            )
        } catch (e: Exception) {
            // Fallback на локальное время при ошибке сети
            AntohaotInfo(
                currentTime = System.currentTimeMillis(),
                elapsedTime = SystemClock.elapsedRealtime(),
            )
        }
    }

    fun addTimeRecord(info: AntohaotInfo) {
        val updatedRecords = _timeRecords.value + info
        _timeRecords.value = updatedRecords
        saveRecords(updatedRecords)
    }

    fun getAllRecords(): StateFlow<List<AntohaotInfo>> {
        return timeRecords
    }

    private fun saveRecords(records: List<AntohaotInfo>) {
        val serializableRecords = records.map { it.toSerializable() }
        val jsonString = json.encodeToString<List<AntohaotInfoSerializable>>(serializableRecords)
        sharedPreferences.edit()
            .putString(recordsKey, jsonString)
            .apply()
    }

    private fun loadRecords(): List<AntohaotInfo> {
        return try {
            val jsonString = sharedPreferences.getString(recordsKey, null)
            if (jsonString != null) {
                val serializableRecords = json.decodeFromString<List<AntohaotInfoSerializable>>(jsonString)
                serializableRecords.map { it.toAntohaotInfo() }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
