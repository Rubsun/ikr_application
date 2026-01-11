package com.example.features.screens

import android.content.Context
import com.example.features.screens.api.ScreensConstants
import com.example.features.screens.domain.ScreenListUseCase
import com.example.features.screens.ui.ScreensFragment
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { ScreenListUseCase() }

                intoSetFactory(ScreensConstants.SCREENS_ID) {
                    ScreenFragmentRouter(R.string.title_screen_list, ScreensFragment::class)
                }
            }
        )
    }
}