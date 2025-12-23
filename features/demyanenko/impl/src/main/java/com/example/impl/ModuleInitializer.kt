package com.example.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.api.Constants
import com.example.injector.AbstractInitializer
import com.example.demyanenko.impl.ui.F1CarFragment
import com.example.impl.data.F1CarRepository
import com.example.impl.data.F1Preferences
import com.example.impl.domain.GetF1CarUseCase
import com.example.impl.ui.F1CarViewModel
import com.example.libs.demyanenkoopenf1.DemyanenkoOpenF1Repository
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

                // ✅ Передаём ОБА параметра!
                factory {
                    F1CarViewModel(
                        getF1CarUseCase = get(),
                        openF1Repository = get()
                    )
                }

                factory<Class<out Fragment>>(named(Constants.DEM_SCREEN)) {
                    F1CarFragment::class.java
                }
            }
        )
    }
}
