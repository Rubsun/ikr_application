package com.grigoran.network.data

import android.content.Context
import com.example.injector.AbstractInitializer
import com.grigoran.network.api.ApiClient
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single<ApiClient> { ApiClientImpl(get()) }
            }
        )
    }
}