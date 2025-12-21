package com.example.primitivestorage

import android.content.Context
import com.example.primitivestorage.api.PrimitiveStorage
import kotlinx.serialization.KSerializer

internal class JsonPrimitiveStorageFactory(
    private val context: Context,
) : PrimitiveStorage.Factory {
    override fun <T> create(name: String, serializer: KSerializer<T>): PrimitiveStorage<T> {
        val jsonSerializer = JsonSerializer(serializer)
        return JsonPrimitiveStorage(
            name = name,
            serializer = jsonSerializer,
            context = context
        )
    }
}