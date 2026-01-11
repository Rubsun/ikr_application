package com.dimmension.impl

import android.content.Context
import com.dimmension.api.Constants
import com.dimmension.api.domain.usecases.AddNameUseCase
import com.dimmension.api.domain.usecases.FetchRandomNamesFromNetworkUseCase
import com.dimmension.api.domain.usecases.FilterNamesUseCase
import com.dimmension.api.domain.usecases.GenerateNamesUseCase
import com.dimmension.api.domain.usecases.GetRandomNameUseCase
import com.dimmension.api.domain.usecases.ObserveNamesUseCase
import com.dimmension.impl.data.NameRepository
import com.dimmension.impl.data.remote.RemoteNameDataSource
import com.dimmension.impl.domain.AddNameUseCaseImpl
import com.dimmension.impl.domain.FetchRandomNamesFromNetworkUseCaseImpl
import com.dimmension.impl.domain.FilterNamesUseCaseImpl
import com.dimmension.impl.domain.GenerateNamesUseCaseImpl
import com.dimmension.impl.domain.GetRandomNameUseCaseImpl
import com.dimmension.impl.domain.ObserveNamesUseCaseImpl
import com.dimmension.impl.ui.NamesFragment
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { NameRepository(get()) }
                single { RemoteNameDataSource(get()) } // Использует RandomUserApi из dimmension-network

                factory<GetRandomNameUseCase> { GetRandomNameUseCaseImpl(get()) }
                factory<ObserveNamesUseCase> { ObserveNamesUseCaseImpl(get()) }
                factory<GenerateNamesUseCase> { GenerateNamesUseCaseImpl(get()) }
                factory<AddNameUseCase> { AddNameUseCaseImpl(get()) }
                factory<FilterNamesUseCase> { FilterNamesUseCaseImpl() }
                factory<FetchRandomNamesFromNetworkUseCase> { 
                    FetchRandomNamesFromNetworkUseCaseImpl(get(), get()) 
                }

                intoSetFactory(Constants.DIMMENSION_SCREEN) {
                    ScreenFragmentRouter(R.string.title_dimmension_screen, NamesFragment::class)
                }
            }
        )
    }
}


