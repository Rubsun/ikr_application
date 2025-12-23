package com.coil.data


import android.content.Context
import com.coil.api.ImageLoader
import com.example.injector.AbstractInitializer
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
