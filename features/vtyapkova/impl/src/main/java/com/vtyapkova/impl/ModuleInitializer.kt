package com.vtyapkova.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.injector.AbstractInitializer
import com.example.primitivestorage.api.PrimitiveStorage
import com.vtyapkova.api.Constants
import com.vtyapkova.impl.data.RandomUserApiClient
import com.vtyapkova.impl.data.ViktoriaRepository
import com.vtyapkova.impl.data.storage.StoredViktoriaData
import com.vtyapkova.impl.domain.AddViktoriaFromApiUseCase
import com.vtyapkova.impl.domain.AddViktoriaUseCase
import com.vtyapkova.impl.domain.FilterViktoriaUseCase
import com.vtyapkova.impl.domain.GetMultipleViktoriaUseCase
import com.vtyapkova.impl.domain.GetRandomViktoriaUseCase
import com.vtyapkova.impl.domain.LoadUserFromApiUseCase
import com.vtyapkova.impl.ui.ViktoriaFragment
import com.vtyapkova.impl.ui.ViktoriaViewModel
import kotlinx.serialization.builtins.ListSerializer
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val STORAGE_NAME = "vtyapkova_names.json"

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single { RandomUserApiClient.create() }

                single { LoadUserFromApiUseCase(get()) }

                single<PrimitiveStorage<List<StoredViktoriaData>>> {
                    val factory: PrimitiveStorage.Factory = get()
                    factory.create(
                        name = STORAGE_NAME,
                        serializer = ListSerializer(StoredViktoriaData.serializer())
                    )
                }

                single { ViktoriaRepository(get(), get()) }

                factory { GetRandomViktoriaUseCase(get()) }
                factory { GetMultipleViktoriaUseCase(get()) }
                factory { FilterViktoriaUseCase(get()) }
                factory { AddViktoriaUseCase(get()) }
                factory { AddViktoriaFromApiUseCase(get()) }

                viewModel {
                    ViktoriaViewModel(
                        getRandomViktoriaUseCase = get(),
                        getMultipleViktoriaUseCase = get(),
                        filterViktoriaUseCase = get(),
                        addViktoriaUseCase = get(),
                        addViktoriaFromApiUseCase = get(),
                    )
                }

                factory<Class<out Fragment>>(named(Constants.VTYAPKOVA_SCREEN)) {
                    ViktoriaFragment::class.java
                }
            }
        )
    }
}
