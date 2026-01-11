package com.zagora.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.injector.AbstractInitializer
import com.example.libs.arch.ScreenFragmentRouter
import com.example.network.api.RetrofitServiceFactory
import com.zagora.api.Constants
import com.zagora.impl.data.DogApiService
import com.zagora.impl.data.Repository
import com.zagora.impl.data.ZagoraStateHolder
import com.zagora.impl.domain.GetDogBreedsUseCase
import com.zagora.impl.domain.GetDogImageUseCase
import com.zagora.impl.ui.FragmentDog
import com.zagora.impl.ui.MyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                intoSetFactory(Constants.ZAGORA_SCREEN) {
                    ScreenFragmentRouter(R.string.zagora_title, FragmentDog::class)
                }
                single<DogApiService> {
                    get<RetrofitServiceFactory>(named("gson"))
                        .create("https://dog.ceo/api/", DogApiService::class.java)
                }
                single {
                    context.getSharedPreferences("zagora_prefs", Context.MODE_PRIVATE)
                }
                factory { ZagoraStateHolder(get()) }
                factory { Repository(get()) }
                factory { GetDogBreedsUseCase(get()) }
                factory { GetDogImageUseCase(get()) }
                viewModel { MyViewModel(get(), get(), get()) }
            }
        )
    }
}