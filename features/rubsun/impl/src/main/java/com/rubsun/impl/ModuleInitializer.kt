package com.rubsun.impl

import android.content.Context
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import com.rubsun.api.Constants
import com.rubsun.api.domain.usecases.GetNumberUseCase
import com.rubsun.impl.data.NumberRepository
import com.rubsun.impl.domain.GetNumberUseCaseImpl
import com.rubsun.impl.ui.NumberFragment
import com.rubsun.impl.ui.NumberViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { 
                    val repo = NumberRepository(get<com.rubsun.storage.api.NumberStorage>(), get<com.rubsun.network.api.NumberApiClient>())
                    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
                    scope.launch {
                        repo.initializeDefaultNumbers()
                    }
                    repo
                }

                factory<GetNumberUseCase> { GetNumberUseCaseImpl(get()) }

                factory { NumberViewModel(get()) }

                intoSetFactory(Constants.RUBSUN_SCREEN) {
                    ScreenFragmentRouter(R.string.title_rubsun, NumberFragment::class)
                }
            }
        )
    }
}

