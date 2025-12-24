package com.dimmension.network.data

import android.content.Context
import com.dimmension.network.api.DimmensionNetworkClient
import com.dimmension.network.api.RandomUserApi
import com.dimmension.network.data.randomuser.RandomUserApiImpl
import com.example.injector.AbstractInitializer
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        val networkClient = RetrofitNetworkClient()
        
        loadKoinModules(
            module {
                single<DimmensionNetworkClient> { networkClient }
                single<RandomUserApi> { RandomUserApiImpl(networkClient) }
            }
        )
    }
}

