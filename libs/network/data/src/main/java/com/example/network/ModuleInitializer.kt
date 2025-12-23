package com.example.network

import android.content.Context
import com.example.injector.AbstractInitializer
import com.example.network.api.RetrofitServiceFactory
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single<RetrofitServiceFactory> { RetrofitServiceFactoryImpl() }
            }
        )
    }
}
