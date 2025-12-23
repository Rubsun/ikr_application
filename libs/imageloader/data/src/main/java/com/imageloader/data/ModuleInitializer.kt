package com.imageloader.data

import android.content.Context
import com.example.injector.AbstractInitializer
import com.imageloader.api.ImageLoader
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single<ImageLoader> { CoilImageLoader(context) }
            }
        )
    }
}