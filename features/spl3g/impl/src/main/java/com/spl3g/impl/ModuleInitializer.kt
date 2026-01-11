package com.spl3g.impl

import android.content.Context
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import com.spl3g.api.Constants
import com.spl3g.api.domain.GetAppleFramesUseCase
import com.spl3g.impl.data.AppleRepository
import com.spl3g.impl.domain.GetAppleFramesUseCaseImpl
import com.spl3g.impl.ui.AppleFramesFragment
import com.spl3g.impl.ui.AppleFramesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { AppleRepository(get(), get()) }
                factory<GetAppleFramesUseCase> { GetAppleFramesUseCaseImpl(get()) }
                viewModel { AppleFramesViewModel(get(), get()) }

                intoSetFactory(Constants.SPL3G_SCREEN) {
                    ScreenFragmentRouter(R.string.title_spl3g, AppleFramesFragment::class)
                }
            }
        )
    }
}
