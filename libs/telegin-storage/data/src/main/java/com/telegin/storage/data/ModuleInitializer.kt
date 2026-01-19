package com.telegin.storage.data

import android.content.Context
import com.example.injector.AbstractInitializer
import com.example.primitivestorage.api.PrimitiveStorage
import com.telegin.storage.api.WeatherStorage
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val STORAGE_NAME = "telegin_storage"

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single<PrimitiveStorage<TeleginStorageState>>(named(STORAGE_NAME)) {
                    val factory: PrimitiveStorage.Factory = get()
                    factory.create(
                        name = STORAGE_NAME,
                        serializer = TeleginStorageState.serializer()
                    )
                }

                single<WeatherStorage> {
                    TeleginStorageImpl(get(named(STORAGE_NAME)))
                }
            }
        )
    }
}

