package com.tire.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.injector.AbstractInitializer
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import com.tire.api.Constants
import com.tire.api.domain.usecases.GetAllCasesUseCase
import com.tire.api.domain.usecases.GetAllPokemonsUseCase
import com.tire.api.domain.usecases.GetCasePreviewUseCase
import com.tire.api.domain.usecases.GetCollectionStatsUseCase
import com.tire.api.domain.usecases.GetMyCollectionUseCase
import com.tire.api.domain.usecases.OpenCaseUseCase
import com.tire.api.domain.usecases.SearchPokemonsUseCase
import com.tire.impl.data.config.CaseConfigLoader
import com.tire.impl.data.repository.PokemonRepository
import com.tire.impl.data.repository.PokemonRepositoryImpl
import com.tire.impl.domain.usecases.GetAllCasesUseCaseImpl
import com.tire.impl.domain.usecases.GetAllPokemonsUseCaseImpl
import com.tire.impl.domain.usecases.GetCasePreviewUseCaseImpl
import com.tire.impl.domain.usecases.GetCollectionStatsUseCaseImpl
import com.tire.impl.domain.usecases.GetMyCollectionUseCaseImpl
import com.tire.impl.domain.usecases.OpenCaseUseCaseImpl
import com.tire.impl.domain.usecases.SearchPokemonsUseCaseImpl
import com.tire.impl.ui.allpokemons.AllPokemonsPagingSource
import com.tire.impl.ui.allpokemons.AllPokemonsViewModel
import com.tire.impl.ui.cases.CasesViewModel
import com.tire.impl.ui.collection.CollectionViewModel
import com.tire.impl.ui.root.RootFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { CaseConfigLoader(context) }
                single<PokemonRepository> {
                    PokemonRepositoryImpl(
                        caseConfigLoader = get(),
                        pokeApiClient = get(),
                        local = get()
                    )
                }
                factory<GetAllCasesUseCase> { GetAllCasesUseCaseImpl(get()) }
                factory<OpenCaseUseCase> { OpenCaseUseCaseImpl(get()) }
                factory<GetMyCollectionUseCase> { GetMyCollectionUseCaseImpl(get()) }
                factory<GetCasePreviewUseCase> { GetCasePreviewUseCaseImpl(get()) }
                factory<GetAllPokemonsUseCase> { GetAllPokemonsUseCaseImpl(get()) }
                factory<GetCollectionStatsUseCase> { GetCollectionStatsUseCaseImpl(get()) }
                factory<SearchPokemonsUseCase> { SearchPokemonsUseCaseImpl(get()) }
                factory { AllPokemonsPagingSource(get(), get()) }

                viewModel { CasesViewModel() }
                viewModel { CollectionViewModel() }
                viewModel { AllPokemonsViewModel() }

                factory<Class<out Fragment>>(named(Constants.TIRE_SCREEN)) {
                    RootFragment::class.java
                }
            }
        )
    }
}
