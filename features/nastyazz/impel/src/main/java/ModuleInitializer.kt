package com.nastyazz.impel.nastyazz

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.injector.AbstractInitializer
import com.example.nastyazz.api.NastyAzzClient
import com.example.primitivestorage.api.PrimitiveStorage
import com.nastyazz.api.Constants
import com.nastyazz.api.domain.usecases.AddItemUseCase
import com.nastyazz.api.domain.usecases.ObserveItemsUseCase
import com.nastyazz.impel.nastyazz.data.ItemRepository
import com.nastyazz.impel.nastyazz.ui.ItemsFragment
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import com.nastyazz.api.domain.usecases.ItemSearchUseCase
import com.nastyazz.api.domain.usecases.ItemSuggestUseCase
import com.nastyazz.impel.nastyazz.domain.ItemSearchUseCaseImpl
import com.nastyazz.impel.nastyazz.domain.ItemSuggestUseCaseImpl
import com.nastyazz.impel.nastyazz.ui.ItemsViewModel

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


                factory<Class<out Fragment>>(named(Constants.NASTYAZZ_SCREEN)) {
                    ItemsFragment::class.java
                }
            }
        )
    }
}