package com.example.primitivestorage

import android.content.Context
import androidx.datastore.dataStore
import com.example.primitivestorage.api.PrimitiveStorage
import kotlinx.coroutines.flow.Flow

internal class JsonPrimitiveStorage<T>(
    name: String,
    serializer: JsonSerializer<T>,
    val context: Context,
) : PrimitiveStorage<T> {
    val Context.dataStore by dataStore(
        fileName = name,
        serializer = serializer,
    )

    override fun get(): Flow<T?> {
        return context.dataStore.data
    }

    override suspend fun patch(block: (T?) -> T?) {
        context.dataStore.updateData(block)
    }

    override suspend fun put(value: T?) {
        context.dataStore.updateData { value }
    }
}