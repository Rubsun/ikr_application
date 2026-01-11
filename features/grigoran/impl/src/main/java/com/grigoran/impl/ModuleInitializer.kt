package com.grigoran.impl

import android.content.Context
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import com.example.primitivestorage.api.PrimitiveStorage
import com.grigoran.api.Constants
import com.grigoran.api.domain.ItemSearchUseCase
import com.grigoran.api.domain.ItemSuggestUseCase
import com.grigoran.api.domain.SortItemUseCase
import com.grigoran.impl.data.Repository
import com.grigoran.impl.domain.ItemSearchUseCaseImpl
import com.grigoran.impl.domain.ItemSuggestUseCaseImpl
import com.grigoran.impl.domain.SortItemsUseCaseImpl
import com.grigoran.impl.ui.GrigoranFragment
import com.grigoran.network.api.ApiClient
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

private const val BASE_URL = "https://dummyjson.com/"

internal class ModuleInitializer : AbstractInitializer<Unit>()  {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { Repository(get<ApiClient>(), get<PrimitiveStorage.Factory>())}
                factory<ItemSearchUseCase> { ItemSearchUseCaseImpl(get())}
                factory<ItemSuggestUseCase> { ItemSuggestUseCaseImpl(get())}
                factory<SortItemUseCase> { SortItemsUseCaseImpl()}

                intoSetFactory(Constants.GRIGORAN_SCREEN) {
                    ScreenFragmentRouter(R.string.grigoran_title, GrigoranFragment::class)
                }
            }


        )
    }

}