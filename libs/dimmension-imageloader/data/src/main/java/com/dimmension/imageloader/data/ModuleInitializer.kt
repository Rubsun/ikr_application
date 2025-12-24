package com.dimmension.imageloader.data

import android.content.Context
import com.dimmension.imageloader.api.DimmensionImageLoader
import com.example.injector.AbstractInitializer
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single<DimmensionImageLoader> { CoilImageLoaderImpl() }
            }
        )
    }
}

