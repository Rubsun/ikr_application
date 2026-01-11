package com.drain678.impl.data

import android.content.Context
import android.content.SharedPreferences
import android.os.SystemClock
import com.drain678.api.domain.models.Drain678Info
import com.drain678.network.api.TimeApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Drain678InfoSerializable(
    val currentTime: Long,
    val elapsedTime: Long,
) {
    fun toDrain678Info(): Drain678Info {
        return Drain678Info(
            currentTime = currentTime,
            elapsedTime = elapsedTime
        )
    }
}

fun Drain678Info.toSerializable(): Drain678InfoSerializable {
    return Drain678InfoSerializable(
        currentTime = currentTime,
        elapsedTime = elapsedTime
    )
}

internal class Drain678Repository(
    private val context: Context,
    private val timeApiClient: TimeApiClient
) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "drain678_prefs",
        Context.MODE_PRIVATE
    )
    private val json = Json { ignoreUnknownKeys = true }
    private val recordsKey = "time_records"

    private val _timeRecords = MutableStateFlow<List<Drain678Info>>(loadRecords())
    val timeRecords: StateFlow<List<Drain678Info>> = _timeRecords.asStateFlow()

    init {
        // Загружаем сохраненные записи при инициализации
        _timeRecords.value = loadRecords()
    }

    /**
     * Получает информацию о времени из интернета с fallback на локальное время при ошибке.
     */
    suspend fun drain678Info(): Drain678Info = withContext(Dispatchers.IO) {
        try {
            // Пытаемся получить время из интернета
            val timeDto = timeApiClient.getCurrentTime("UTC")
            Drain678Info(
                currentTime = timeDto.unixtime * 1000, // Конвертируем секунды в миллисекунды
                elapsedTime = SystemClock.elapsedRealtime(),
            )
        } catch (e: Exception) {
            // Fallback на локальное время при ошибке сети
            Drain678Info(
                currentTime = System.currentTimeMillis(),
                elapsedTime = SystemClock.elapsedRealtime(),
            )
        }
    }

    fun addTimeRecord(info: Drain678Info) {
        val updatedRecords = _timeRecords.value + info
        _timeRecords.value = updatedRecords
        saveRecords(updatedRecords)
    }

    fun getAllRecords(): StateFlow<List<Drain678Info>> {
        return timeRecords
    }

    private fun saveRecords(records: List<Drain678Info>) {
        val serializableRecords = records.map { it.toSerializable() }
        val jsonString = json.encodeToString<List<Drain678InfoSerializable>>(serializableRecords)
        sharedPreferences.edit()
            .putString(recordsKey, jsonString)
            .apply()
    }

    private fun loadRecords(): List<Drain678Info> {
        return try {
            val jsonString = sharedPreferences.getString(recordsKey, null)
            if (jsonString != null) {
                val serializableRecords = json.decodeFromString<List<Drain678InfoSerializable>>(jsonString)
                serializableRecords.map { it.toDrain678Info() }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}

