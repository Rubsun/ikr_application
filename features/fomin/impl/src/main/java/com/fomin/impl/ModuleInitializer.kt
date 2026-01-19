package com.fomin.impl

import android.content.Context
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import com.example.primitivestorage.api.PrimitiveStorage
import com.fomin.api.Constants
import com.fomin.api.domain.usecases.GetBreedDetailsUseCase
import com.fomin.api.domain.usecases.GetBreedImagesUseCase
import com.fomin.api.domain.usecases.GetBreedsUseCase
import com.fomin.impl.data.CatRepository
import com.fomin.impl.data.FominStorage
import com.fomin.impl.domain.GetBreedDetailsUseCaseImpl
import com.fomin.impl.domain.GetBreedImagesUseCaseImpl
import com.fomin.impl.domain.GetBreedsUseCaseImpl
import com.fomin.impl.ui.BreedDetailViewModel
import com.fomin.impl.ui.FominFragment
import com.fomin.impl.ui.FominViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val STORAGE_NAME = "fomin_storage"

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { CatRepository(get()) }

                single<PrimitiveStorage<FominStorage.State>>(named(STORAGE_NAME)) {
                    val factory: PrimitiveStorage.Factory = get()
                    factory.create(
                        name = STORAGE_NAME,
                        serializer = FominStorage.State.serializer()
                    )
                }

                single { FominStorage(get(named(STORAGE_NAME))) }

                factory<GetBreedsUseCase> { GetBreedsUseCaseImpl(get()) }
                factory<GetBreedDetailsUseCase> { GetBreedDetailsUseCaseImpl(get(), get()) }
                factory<GetBreedImagesUseCase> { GetBreedImagesUseCaseImpl(get()) }

                viewModel { FominViewModel() }
                viewModel { BreedDetailViewModel() }

                intoSetFactory(Constants.FOMIN_SCREEN) {
                    ScreenFragmentRouter(R.string.title_fomin, FominFragment::class)
                }
            }
        )
    }
}

