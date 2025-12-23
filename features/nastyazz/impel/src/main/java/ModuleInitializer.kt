package com.nastyazz.impel.nastyazz

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.injector.AbstractInitializer
import com.nastyazz.api.Constants
import com.nastyazz.api.domain.usecases.AddItemUseCase
import com.nastyazz.api.domain.usecases.ObserveItemsUseCase
import com.nastyazz.impel.nastyazz.data.FakeItemRepository
import com.nastyazz.impel.nastyazz.data.ItemRepository
import com.nastyazz.impel.nastyazz.domain.AddItemUseCaseImpl
import com.nastyazz.impel.nastyazz.domain.ObserveItemsUseCaseImpl
import com.nastyazz.impel.nastyazz.ui.ItemsFragment
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {

    override fun create(context: Context) {
        loadKoinModules(
            module {
                single<ItemRepository> { FakeItemRepository() }
                factory<ObserveItemsUseCase> { ObserveItemsUseCaseImpl(get()) }
                factory<AddItemUseCase> { AddItemUseCaseImpl(get()) }
                factory<Class<out Fragment>>(named(Constants.NASTYAZZ_SCREEN)) {
                    ItemsFragment::class.java
                }
            }
        )
    }
}
