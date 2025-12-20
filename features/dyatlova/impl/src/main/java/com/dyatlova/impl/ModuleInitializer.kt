package com.dyatlova.impl

import android.content.Context
import androidx.fragment.app.Fragment
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
import com.example.injector.AbstractInitializer
import com.example.primitivestorage.JsonPrimitiveStorageFactory
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { JsonPrimitiveStorageFactory(context) }
                single { DestinationRepository(get()) }

                factory<ObserveDestinationsUseCase> { ObserveDestinationsUseCaseImpl(get()) }
                factory<AddDestinationUseCase> { AddDestinationUseCaseImpl(get()) }
                factory<FilterDestinationsUseCase> { FilterDestinationsUseCaseImpl() }

                factory { DyatlovaViewModel(get(), get(), get()) }

                factory<Class<out Fragment>>(named(Constants.DYATLOVA_SCREEN)) {
                    DyatlovaFragment::class.java
                }
            }
        )
    }
}

