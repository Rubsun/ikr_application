package com.kristevt.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.injector.AbstractInitializer
import com.kristevt.api.Constants
import com.kristevt.api.domain.GetLyricsUseCase
import com.kristevt.api.domain.SongsListUseCase
import com.kristevt.impl.data.SongsRepository
import com.kristevt.impl.domain.GetLyricsUseCaseImpl
import com.kristevt.impl.domain.SongsListUseCaseImpl
import com.kristevt.impl.ui.KristevtFragment
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * ModuleInitializer feature-модуля kristevt.
 *
 * Не содержит сетевых зависимостей.
 * Использует только абстракции (UseCase, DataSource).
 */
internal class ModuleInitializer : AbstractInitializer<Unit>() {

    override fun create(context: Context) {
        loadKoinModules(
            module {

                single { SongsRepository(context = get())}
                // UseCases
                factory<SongsListUseCase> {
                    SongsListUseCaseImpl(get())
                }

                factory<GetLyricsUseCase> {
                    GetLyricsUseCaseImpl(get())
                }

                // Навигация / UI
                factory<Class<out Fragment>>(named(Constants.KRISTEVT_SCREEN)) {
                    KristevtFragment::class.java
                }
            }
        )
    }
}
