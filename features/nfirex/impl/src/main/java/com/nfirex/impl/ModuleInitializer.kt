package com.nfirex.impl

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.injector.AbstractInitializer
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nfirex.api.Constants
import com.nfirex.api.domain.usecases.EmojiListUseCase
import com.nfirex.api.domain.usecases.EmojiSuggestUseCase
import com.nfirex.impl.data.EmojiRepository
import com.nfirex.impl.data.EmojiService
import com.nfirex.impl.domain.EmojiListUseCaseImpl
import com.nfirex.impl.domain.EmojiSuggestUseCaseImpl
import com.nfirex.impl.ui.EmojiListFragment
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

private const val BASE_URL = "https://emojihub.yurace.pro/"

/**
 * Модуль для конфигурации зависимостей
 * Его надо добавлять в манифест, что бы KOIN + startup их собрали
 */
internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        // Добавление в KOIN модулей
        loadKoinModules(
            // Создаем и добавляем модуль
            module {
                // factory правило - все время создаем новый инстанс
                factory { createService(BASE_URL) }

                // single правило - создаем инстанс один раз и всегда его отдаем
                single { EmojiRepository(get(), get()) }

                // жесткое проставление типа.
                // Нужно когда все работают с интерфейсом, а нам надо вметсо него поставить имплементацию
                factory<EmojiListUseCase> { EmojiListUseCaseImpl(get()) }
                factory<EmojiSuggestUseCase> { EmojiSuggestUseCaseImpl(get()) }

                // named - квалифаер зависимости
                // Если у нас етсь много поставщиков одного типа, и мы хотим их как-то разделить
                factory<Class<out Fragment>>(named(Constants.NFIREX_SCREEN)) { EmojiListFragment::class.java}
            }
        )
    }

    private fun createService(baseUrl: String): EmojiService {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val json = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(EmojiService::class.java)
    }
}