package com.spl3g.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.spl3g.api.Constants
import com.spl3g.api.domain.GetAppleFramesUseCase
import com.spl3g.impl.data.AppleRepository
import com.spl3g.impl.domain.GetAppleFramesUseCaseImpl
import com.spl3g.impl.ui.AppleFramesFragment
import com.spl3g.impl.ui.AppleFramesViewModel
import com.example.injector.AbstractInitializer
import com.example.primitivestorage.api.PrimitiveStorage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { AppleRepository(get(), get()) }
                factory<GetAppleFramesUseCase> { GetAppleFramesUseCaseImpl(get()) }
                viewModel { AppleFramesViewModel(get(), get()) }
                factory<Class<out Fragment>>(named(Constants.SPL3G_SCREEN)) {
                    AppleFramesFragment::class.java
                }
            }
        )
    }
}
