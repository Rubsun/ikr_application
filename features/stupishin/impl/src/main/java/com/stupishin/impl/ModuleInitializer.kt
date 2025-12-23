package com.stupishin.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.injector.AbstractInitializer
import com.example.jikan.api.JikanClient
import com.example.primitivestorage.api.PrimitiveStorage
import com.stupishin.api.Constants
import com.stupishin.api.domain.usecases.GetTopAnimeUseCase
import com.stupishin.api.domain.usecases.SearchAnimeUseCase
import com.stupishin.impl.data.AnimeRepository
import com.stupishin.impl.data.AnimeService
import com.stupishin.impl.data.AnimeServiceImpl
import com.stupishin.impl.data.StupishinStorage
import com.stupishin.impl.domain.GetTopAnimeUseCaseImpl
import com.stupishin.impl.domain.SearchAnimeUseCaseImpl
import com.stupishin.impl.ui.StuAnimeFragment
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single<AnimeService> {
                    AnimeServiceImpl(get<JikanClient>())
                }

                single {
                    val primitive = get<PrimitiveStorage.Factory>().create(
                        name = "stupishin_storage",
                        serializer = StupishinStorage.State.serializer()
                    )
                    StupishinStorage(primitive)
                }

                single {
                    AnimeRepository(get())
                }

                factory<GetTopAnimeUseCase> {
                    GetTopAnimeUseCaseImpl(get(), get())
                }

                factory<SearchAnimeUseCase> {
                    SearchAnimeUseCaseImpl(get(), get())
                }

                factory<Class<out Fragment>>(named(Constants.STUPISHIN_SCREEN)) {
                    StuAnimeFragment::class.java
                }
            }
        )
    }
}
