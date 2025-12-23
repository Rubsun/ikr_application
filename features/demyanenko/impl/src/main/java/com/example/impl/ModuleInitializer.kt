package com.example.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.api.Constants
import com.example.injector.AbstractInitializer
import com.example.demyanenko.impl.ui.F1CarFragment
import com.example.impl.data.F1CarRepository
import com.example.impl.data.F1Preferences
import com.example.impl.domain.GetF1CarUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { F1Preferences(context) }
                single { F1CarRepository(get()) }
                factory { GetF1CarUseCase(get()) }

                // ✅ Register Fragment INSTANCE (not Class)
                factory<Class<out Fragment>>(named(Constants.DEM_SCREEN)) {
                    F1CarFragment::class.java
                }
            }
        )
    }
}

