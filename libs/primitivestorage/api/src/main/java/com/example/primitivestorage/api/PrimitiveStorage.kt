package com.example.primitivestorage.api

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer

interface PrimitiveStorage<T> {
    interface Factory {
        fun <T> create(name: String, serializer: KSerializer<T>): PrimitiveStorage<T>
    }

    fun get(): Flow<T?>

    suspend fun patch(block: (T?) -> T?)

    suspend fun put(value: T?)
}