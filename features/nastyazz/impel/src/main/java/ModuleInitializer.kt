package com.nastyazz.impel.nastyazz

import android.content.Context
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import com.example.nastyazz.api.NastyAzzClient
import com.example.primitivestorage.api.PrimitiveStorage
import com.nastyazz.api.Constants
import com.nastyazz.api.domain.usecases.ItemSearchUseCase
import com.nastyazz.api.domain.usecases.ItemSuggestUseCase
import com.nastyazz.impel.R
import com.nastyazz.impel.nastyazz.data.ItemRepository
import com.nastyazz.impel.nastyazz.domain.ItemSearchUseCaseImpl
import com.nastyazz.impel.nastyazz.domain.ItemSuggestUseCaseImpl
import com.nastyazz.impel.nastyazz.ui.ItemsFragment
import com.nastyazz.impel.nastyazz.ui.ItemsViewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {

    override fun create(context: Context) {
        loadKoinModules(
            module {
                single {
                    ItemRepository(
                        client = get<NastyAzzClient>(),
                        storageFactory = get<PrimitiveStorage.Factory>()
                    )
                }
                factory{ ItemsViewModel(get(), get())}

                factory<ItemSearchUseCase> { ItemSearchUseCaseImpl(get()) }
                factory<ItemSuggestUseCase> { ItemSuggestUseCaseImpl(get()) }

                intoSetFactory(Constants.NASTYAZZ_SCREEN) {
                    ScreenFragmentRouter(R.string.nastyazz_title, ItemsFragment::class)
                }
            }
        )
    }
}