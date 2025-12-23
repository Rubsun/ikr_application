package com.artemkaa.impl.data

import android.content.Context
import android.content.SharedPreferences
import android.os.SystemClock
import com.artemkaa.api.domain.models.ArtemkaaInfo
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
data class ArtemkaaInfoSerializable(
    val currentTime: Long,
    val elapsedTime: Long,
) {
    fun toArtemkaaInfo(): ArtemkaaInfo {
        return ArtemkaaInfo(
            currentTime = currentTime,
            elapsedTime = elapsedTime
        )
    }
}

fun ArtemkaaInfo.toSerializable(): ArtemkaaInfoSerializable {
    return ArtemkaaInfoSerializable(
        currentTime = currentTime,
        elapsedTime = elapsedTime
    )
}

internal class ArtemkaaRepository(
    private val context: Context
) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "artemkaa_prefs",
        Context.MODE_PRIVATE
    )
    private val json = Json { ignoreUnknownKeys = true }
    private val recordsKey = "time_records"

    private val _timeRecords = MutableStateFlow<List<ArtemkaaInfo>>(loadRecords())
    val timeRecords: StateFlow<List<ArtemkaaInfo>> = _timeRecords.asStateFlow()

    init {
        // Загружаем сохраненные записи при инициализации
        _timeRecords.value = loadRecords()
    }

    fun artemkaaInfo(): ArtemkaaInfo {
        return ArtemkaaInfo(
            currentTime = System.currentTimeMillis(),
            elapsedTime = SystemClock.elapsedRealtime(),
        )
    }

    fun addTimeRecord(info: ArtemkaaInfo) {
        val updatedRecords = _timeRecords.value + info
        _timeRecords.value = updatedRecords
        saveRecords(updatedRecords)
    }

    fun getAllRecords(): StateFlow<List<ArtemkaaInfo>> {
        return timeRecords
    }

    private fun saveRecords(records: List<ArtemkaaInfo>) {
        val serializableRecords = records.map { it.toSerializable() }
        val jsonString = json.encodeToString<List<ArtemkaaInfoSerializable>>(serializableRecords)
        sharedPreferences.edit()
            .putString(recordsKey, jsonString)
            .apply()
    }

    private fun loadRecords(): List<ArtemkaaInfo> {
        return try {
            val jsonString = sharedPreferences.getString(recordsKey, null)
            if (jsonString != null) {
                val serializableRecords = json.decodeFromString<List<ArtemkaaInfoSerializable>>(jsonString)
                serializableRecords.map { it.toArtemkaaInfo() }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}

