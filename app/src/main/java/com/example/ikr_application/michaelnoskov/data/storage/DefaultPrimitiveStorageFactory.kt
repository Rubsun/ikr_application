package com.example.ikr_application.michaelnoskov.data.storage

import android.content.Context
import com.example.primitivestorage.api.PrimitiveStorage
import kotlinx.serialization.KSerializer

class DefaultPrimitiveStorageFactory(
    private val context: Context
) : PrimitiveStorage.Factory {

    override fun <T> create(name: String, serializer: KSerializer<T>): PrimitiveStorage<T> {
        return SharedPreferencesPrimitiveStorage(context, name, serializer)
    }
}