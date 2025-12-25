package com.argun.impl.data

import android.content.Context
import android.content.SharedPreferences
import android.os.SystemClock
import com.argun.api.domain.models.ArgunInfo
import com.argun.api.domain.models.Zadacha
import com.argun.network.api.TimeApiClient
import com.argun.network.api.ZadachiApiClient
import com.argun.network.api.models.ZadachaDto
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
data class ArgunInfoSerializable(
    val currentTime: Long,
    val elapsedTime: Long,
) {
    fun toArgunInfo(): ArgunInfo {
        return ArgunInfo(
            currentTime = currentTime,
            elapsedTime = elapsedTime
        )
    }
}

fun ArgunInfo.toSerializable(): ArgunInfoSerializable {
    return ArgunInfoSerializable(
        currentTime = currentTime,
        elapsedTime = elapsedTime
    )
}

internal class ArgunRepository(
    private val context: Context,
    private val timeApiClient: TimeApiClient,
    private val zadachiApiClient: ZadachiApiClient
) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "argun_prefs",
        Context.MODE_PRIVATE
    )
    private val json = Json { ignoreUnknownKeys = true }
    private val recordsKey = "time_records"

    private val _timeRecords = MutableStateFlow<List<ArgunInfo>>(loadRecords())
    val timeRecords: StateFlow<List<ArgunInfo>> = _timeRecords.asStateFlow()

    init {
        _timeRecords.value = loadRecords()
    }

    suspend fun argunInfo(): ArgunInfo = withContext(Dispatchers.IO) {
        try {
            val timeDto = timeApiClient.getCurrentTime("UTC")
            ArgunInfo(
                currentTime = timeDto.unixtime * 1000,
                elapsedTime = SystemClock.elapsedRealtime(),
            )
        } catch (e: Exception) {
            ArgunInfo(
                currentTime = System.currentTimeMillis(),
                elapsedTime = SystemClock.elapsedRealtime(),
            )
        }
    }

    fun addTimeRecord(info: ArgunInfo) {
        val updatedRecords = _timeRecords.value + info
        _timeRecords.value = updatedRecords
        saveRecords(updatedRecords)
    }

    fun getAllRecords(): StateFlow<List<ArgunInfo>> {
        return timeRecords
    }

    private fun saveRecords(records: List<ArgunInfo>) {
        val serializableRecords = records.map { it.toSerializable() }
        val jsonString = json.encodeToString<List<ArgunInfoSerializable>>(serializableRecords)
        sharedPreferences.edit()
            .putString(recordsKey, jsonString)
            .apply()
    }

    private fun loadRecords(): List<ArgunInfo> {
        return try {
            val jsonString = sharedPreferences.getString(recordsKey, null)
            if (jsonString != null) {
                val serializableRecords = json.decodeFromString<List<ArgunInfoSerializable>>(jsonString)
                serializableRecords.map { it.toArgunInfo() }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getAllZadachi(): List<Zadacha> = withContext(Dispatchers.IO) {
        try {
            val zadachiDto = zadachiApiClient.vseZadachi()
            zadachiDto.map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getZadachaById(id: Int): Zadacha? = withContext(Dispatchers.IO) {
        try {
            val zadachaDto = zadachiApiClient.zadachaPoId(id)
            zadachaDto.toDomain()
        } catch (e: Exception) {
            null
        }
    }

    private fun ZadachaDto.toDomain(): Zadacha {
        return Zadacha(
            id = id,
            title = title,
            completed = completed,
            userId = userId
        )
    }
}

