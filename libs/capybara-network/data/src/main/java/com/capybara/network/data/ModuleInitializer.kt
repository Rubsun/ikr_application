package com.capybara.network.data

import android.content.Context
import com.capybara.network.api.CapybaraApiClient
import com.example.injector.AbstractInitializer
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single<CapybaraApiClient> { RetrofitCapybaraApiClient(get()) }
            }
        )
    }
}
