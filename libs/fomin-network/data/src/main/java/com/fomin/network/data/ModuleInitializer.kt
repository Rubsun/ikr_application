package com.fomin.network.data

import android.content.Context
import com.example.injector.AbstractInitializer
import com.fomin.network.api.CatApiClient
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single<CatApiClient> { CatApiClientImpl(get()) }
            }
        )
    }
}


