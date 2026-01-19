package com.eremin.impl

import android.content.Context
import com.eremin.api.Constants
import com.eremin.impl.data.CapybaraRepository
import com.eremin.impl.domain.GetCapybarasUseCase
import com.eremin.impl.ui.EreminFragment
import com.eremin.impl.ui.EreminViewModel
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import com.example.primitivestorage.api.PrimitiveStorage
import kotlinx.serialization.builtins.serializer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        val ereminModule = module {
            factory { CapybaraRepository(get()) }
            factory { GetCapybarasUseCase(get()) }
            single(named("eremin_storage")) {
                get<PrimitiveStorage.Factory>().create("eremin_search_history.json", String.serializer()) 
            }
            viewModel {
                EreminViewModel(
                    getCapybarasUseCase = get(),
                    primitiveStorage = get(named("eremin_storage"))
                )
            }
            
            intoSetFactory(Constants.EREMIN_SCREEN) {
                ScreenFragmentRouter(R.string.eremin_title, EreminFragment::class)
            }
        }
        loadKoinModules(ereminModule)
    }
}
