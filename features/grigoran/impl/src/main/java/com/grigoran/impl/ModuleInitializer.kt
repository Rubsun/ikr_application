package com.grigoran.impl

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.example.injector.AbstractInitializer
import com.grigoran.api.Constants
import com.grigoran.api.domain.AddItemUseCase
import com.grigoran.impl.data.Repository
import com.grigoran.impl.domain.ItemUseCases
import com.grigoran.api.domain.GetItemUseCase
import com.grigoran.impl.domain.AddItemUseCaseImpl
import com.grigoran.impl.ui.GrigoranFragment
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>()  {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { Repository()}
                factory<GetItemUseCase> { ItemUseCases(get())}
                factory<AddItemUseCase> { AddItemUseCaseImpl(get())}
                factory<Class<out Fragment>>(named(Constants.GRIGORAN_SCREEN)) {
                    GrigoranFragment::class.java
                }
            }


        )
    }
}