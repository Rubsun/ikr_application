package com.example.jikan

import android.content.Context
import com.example.injector.AbstractInitializer
import com.example.jikan.api.JikanClient
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single<JikanClient> { JikanClientImpl(get()) }
            }
        )
    }
}
