package com.kristevt.lyrics.impl

import com.kristevt.lyrics.api.LyricsDataSource
import com.kristevt.lyrics.impl.network.RetrofitFactory
import org.koin.dsl.module

private const val BASE_URL = "https://api.lyrics.ovh/"

val lyricsModule = module {

    single {
        RetrofitFactory.create(BASE_URL)
    }

    single<LyricsDataSource> {
        LyricsRepository(get())
    }
}
