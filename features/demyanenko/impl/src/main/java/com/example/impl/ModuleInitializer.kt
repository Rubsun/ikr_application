package com.example.impl

import android.content.Context
import com.example.api.Constants
import com.example.demyanenko.impl.ui.F1CarFragment
import com.example.impl.data.F1CarRepository
import com.example.impl.data.F1Preferences
import com.example.impl.domain.GetF1CarUseCase
import com.example.impl.ui.F1CarViewModel
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import org.koin.core.context.loadKoinModules
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

                intoSetFactory(Constants.DEM_SCREEN) {
                    ScreenFragmentRouter(R.string.demyanenko_title, F1CarFragment::class)
                }
            }
        )
    }
}
