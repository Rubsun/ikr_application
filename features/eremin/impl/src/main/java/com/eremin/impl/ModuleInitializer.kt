package com.eremin.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.eremin.api.Constants
import com.eremin.impl.data.CapybaraRepository
import com.eremin.impl.domain.GetCapybarasUseCase
import com.eremin.impl.ui.EreminFragment
import com.example.injector.AbstractInitializer
import com.example.primitivestorage.api.PrimitiveStorage
import kotlinx.serialization.builtins.serializer
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        val ereminModule = module {
            factory { CapybaraRepository() }
            factory { GetCapybarasUseCase(get()) }
            factory<Class<out Fragment>>(named(Constants.EREMIN_SCREEN)) { EreminFragment::class.java }
            single(named("eremin_storage")) { 
                get<PrimitiveStorage.Factory>().create("eremin_search_history.json", String.serializer()) 
            }
        }
        loadKoinModules(ereminModule)
    }
}
