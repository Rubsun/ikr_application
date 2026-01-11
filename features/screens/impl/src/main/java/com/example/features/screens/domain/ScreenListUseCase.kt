package com.example.features.screens.domain

import com.example.features.screens.api.ScreensConstants
import com.example.injector.all
import com.example.libs.arch.ScreenFragmentRouter

internal class ScreenListUseCase {
    operator fun invoke(): List<Screen> {
        return all<ScreenFragmentRouter>()
            .mapNotNull { pair ->
                if (pair.first == ScreensConstants.SCREENS_ID) {
                    return@mapNotNull null
                }

                Screen(
                    title = pair.second.title,
                    name = pair.first
                )
            }
    }
}