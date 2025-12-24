package com.grigoran.impl

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.example.injector.AbstractInitializer
import com.grigoran.api.Constants
import com.grigoran.api.domain.AddItemUseCase
import com.grigoran.impl.data.Repository
import com.grigoran.api.domain.ItemSearchUseCase
import com.grigoran.api.domain.ItemSuggestUseCase
import com.grigoran.api.domain.SortItemUseCase
import com.grigoran.impl.data.ItemService

import com.grigoran.impl.domain.ItemSearchUseCaseImpl
import com.grigoran.impl.domain.ItemSuggestUseCaseImpl
import com.grigoran.impl.domain.SortItemsUseCaseImpl
import com.grigoran.impl.ui.GrigoranFragment
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory

private const val BASE_URL = "https://dummyjson.com/"

internal class ModuleInitializer : AbstractInitializer<Unit>()  {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                factory { createService(BASE_URL) }
                single { Repository(get(), get())}
                factory<ItemSearchUseCase> { ItemSearchUseCaseImpl(get())}
                factory<ItemSuggestUseCase> { ItemSuggestUseCaseImpl(get())}
                factory<SortItemUseCase> { SortItemsUseCaseImpl()}
                factory<Class<out Fragment>>(named(Constants.GRIGORAN_SCREEN)) {
                    GrigoranFragment::class.java
                }
            }


        )
    }
    private fun createService(baseUrl: String): ItemService {
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(OkHttpClient.Builder().build())
            .build()
            .create(ItemService::class.java)


    }
}