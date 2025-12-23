package com.zagora.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.injector.AbstractInitializer
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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                factory<Class<out Fragment>>(named(Constants.ZAGORA_SCREEN)) {
                    FragmentDog::class.java
                }
                single {
                    Retrofit.Builder()
                        .baseUrl("https://dog.ceo/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create<DogApiService>()
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