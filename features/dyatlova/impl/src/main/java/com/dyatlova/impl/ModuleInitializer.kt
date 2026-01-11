package com.dyatlova.impl

import android.content.Context
import com.dyatlova.api.Constants
import com.dyatlova.api.domain.usecases.AddDestinationUseCase
import com.dyatlova.api.domain.usecases.FilterDestinationsUseCase
import com.dyatlova.api.domain.usecases.ObserveDestinationsUseCase
import com.dyatlova.impl.data.DestinationRepository
import com.dyatlova.impl.domain.AddDestinationUseCaseImpl
import com.dyatlova.impl.domain.FilterDestinationsUseCaseImpl
import com.dyatlova.impl.domain.ObserveDestinationsUseCaseImpl
import com.dyatlova.impl.ui.DyatlovaFragment
import com.dyatlova.impl.ui.DyatlovaViewModel
import com.dyatlova.network.DestinationsRemoteSourceFactory
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { DestinationRepository(get(), get()) }
                single { DestinationsRemoteSourceFactory.create() }

                factory<ObserveDestinationsUseCase> { ObserveDestinationsUseCaseImpl(get()) }
                factory<AddDestinationUseCase> { AddDestinationUseCaseImpl(get()) }
                factory<FilterDestinationsUseCase> { FilterDestinationsUseCaseImpl() }

                factory { DyatlovaViewModel(get(), get(), get()) }

                intoSetFactory(Constants.DYATLOVA_SCREEN) {
                    ScreenFragmentRouter(R.string.dyatlova_title, DyatlovaFragment::class)
                }
            }
        )
    }
}



