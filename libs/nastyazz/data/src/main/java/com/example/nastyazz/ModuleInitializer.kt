package com.example.nastyazz

import android.content.Context
import com.example.injector.AbstractInitializer
import com.example.nastyazz.api.NastyAzzClient
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single<NastyAzzClient> { NastyAzzClientImpl(get()) }
            }
        )
    }
}
