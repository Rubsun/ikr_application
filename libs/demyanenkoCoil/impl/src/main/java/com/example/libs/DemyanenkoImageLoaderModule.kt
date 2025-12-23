package com.example.libs

import android.content.Context
import com.example.demyanenkocoil.api.DemyanenkoImageLoader
import com.example.injector.AbstractInitializer
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


internal class DemyanenkoImageLoaderModule : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single<DemyanenkoImageLoader> { CoilDemyanenkoImageLoader() as DemyanenkoImageLoader }
            }
        )
    }
}
