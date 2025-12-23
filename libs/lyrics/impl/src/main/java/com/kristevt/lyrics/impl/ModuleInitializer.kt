package com.kristevt.lyrics.impl

import android.content.Context
import com.example.injector.AbstractInitializer
import org.koin.core.context.loadKoinModules

/**
 * Инициализатор lyrics-библиотеки.
 *
 * Регистрирует Koin-модуль с сетевой реализацией получения текстов песен.
 * Не содержит UI и не имеет побочных эффектов, кроме DI.
 */
internal class ModuleInitializer : AbstractInitializer<Unit>() {

    override fun create(context: Context) {
        loadKoinModules(lyricsModule)
    }
}
