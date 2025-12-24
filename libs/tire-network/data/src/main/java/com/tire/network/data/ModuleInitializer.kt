package com.tire.network.data

import android.content.Context
import com.example.injector.AbstractInitializer
import com.example.network.api.RetrofitServiceFactory
import com.tire.network.api.PokeRemoteDataSource
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single<PokeRemoteDataSource> {
                    PokeApiClient(get<RetrofitServiceFactory>())
                }
            }
        )
    }
}
