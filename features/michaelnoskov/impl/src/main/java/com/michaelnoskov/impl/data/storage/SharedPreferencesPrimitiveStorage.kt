package com.michaelnoskov.impl.data.storage

import android.content.Context
import com.example.primitivestorage.api.PrimitiveStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

internal class SharedPreferencesPrimitiveStorage<T>(
    private val context: Context,
    private val storageName: String,
    private val serializer: KSerializer<T>
) : PrimitiveStorage<T> {

    private val prefs = context.getSharedPreferences("primitive_storage_$storageName", Context.MODE_PRIVATE)
    private val key = "data"
    private val _flow = MutableStateFlow<T?>(null)

    init {
        // Инициализируем flow текущим значением
        val currentValue = loadFromPrefs()
        _flow.value = currentValue
    }

    override fun get(): Flow<T?> = _flow

    override suspend fun patch(block: (T?) -> T?) {
        val current = _flow.value
        val newValue = block(current)
        saveToPrefs(newValue)
        _flow.value = newValue
    }

    override suspend fun put(value: T?) {
        saveToPrefs(value)
        _flow.value = value
    }

    private fun loadFromPrefs(): T? {
        val json = prefs.getString(key, null) ?: return null
        return try {
            Json.decodeFromString(serializer, json)
        } catch (e: Exception) {
            null
        }
    }

    private fun saveToPrefs(value: T?) {
        if (value == null) {
            prefs.edit().remove(key).apply()
        } else {
            val json = Json.encodeToString(serializer, value)
            prefs.edit().putString(key, json).apply()
        }
    }
}

