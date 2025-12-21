package com.example.primitivestorage

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

internal class JsonSerializer<T>(
    private val serializer: KSerializer<T>,
) : Serializer<T?> {
    override val defaultValue = null

    override suspend fun readFrom(input: InputStream): T? {
        val bytes = input.readBytes()
        if (bytes.isEmpty()) {
            return null
        }

        try {
            return Json.decodeFromString(
                serializer,
                bytes.decodeToString(),
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read Settings", serialization)
        }
    }

    override suspend fun writeTo(t: T?, output: OutputStream) {
        val json = when {
            t == null -> ""
            else -> Json.encodeToString(serializer,t)
        }
        
        val bytes = json.encodeToByteArray()
        
        output.write(bytes)
    }
}