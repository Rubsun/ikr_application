package com.nastyazz.impel.nastyazz

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.injector.AbstractInitializer
import com.example.primitivestorage.api.PrimitiveStorage
import com.nastyazz.api.Constants
import com.nastyazz.api.domain.usecases.AddItemUseCase
import com.nastyazz.api.domain.usecases.ObserveItemsUseCase
import com.nastyazz.impel.nastyazz.data.ItemRepository
import com.nastyazz.impel.nastyazz.data.NastyAzzApi
import com.nastyazz.impel.nastyazz.ui.ItemsFragment
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nastyazz.api.domain.usecases.ItemSearchUseCase
import com.nastyazz.api.domain.usecases.ItemSuggestUseCase
import com.nastyazz.impel.nastyazz.domain.ItemSearchUseCaseImpl
import com.nastyazz.impel.nastyazz.domain.ItemSuggestUseCaseImpl
import com.nastyazz.impel.nastyazz.ui.ItemsViewModel

private const val BASE_URL = "https://dummyjson.com/"

internal class ModuleInitializer : AbstractInitializer<Unit>() {

    override fun create(context: Context) {
        loadKoinModules(
            module {

                factory { createService(BASE_URL) }


                single {
                    ItemRepository(
                        api = get(),
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

    private fun createService(baseUrl: String): NastyAzzApi {
        val json = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .client(OkHttpClient.Builder().build())
            .build()
            .create(NastyAzzApi::class.java)
    }
}