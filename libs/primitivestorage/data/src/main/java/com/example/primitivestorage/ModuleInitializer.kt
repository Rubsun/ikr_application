package com.example.primitivestorage

import android.content.Context
import com.example.injector.AbstractInitializer
import com.example.primitivestorage.api.PrimitiveStorage
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single<PrimitiveStorage.Factory> { JsonPrimitiveStorageFactory(get()) }
            }
        )
    }
}