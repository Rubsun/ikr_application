package com.argun.network.data

import android.content.Context
import com.argun.network.api.AuthManager
import com.argun.network.api.PolzovateliApiClient
import com.argun.network.api.TimeApiClient
import com.argun.network.api.ZadachiApiClient
import com.example.injector.AbstractInitializer
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

internal class ModuleInitializer : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                single {
                    Json { ignoreUnknownKeys = true }
                }
                
                single<AuthManager> {
                    SharedPrefsAuthManager(context)
                }
                
                single {
                    val authManager = get<AuthManager>()
                    val loggingInterceptor = HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                    OkHttpClient.Builder()
                        .addInterceptor(AuthInterceptor(authManager))
                        .addInterceptor(ErrorInterceptor(authManager))
                        .addInterceptor(loggingInterceptor)
                        .build()
                }
                
                single<ZadachiService> {
                    Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(get())
                        .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
                        .build()
                        .create(ZadachiService::class.java)
                }
                
                single<PolzovateliService> {
                    Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(get())
                        .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
                        .build()
                        .create(PolzovateliService::class.java)
                }
                
                single<ZadachiApiClient> {
                    RetrofitZadachiApiClient(get())
                }
                
                single<PolzovateliApiClient> {
                    RetrofitPolzovateliApiClient(get())
                }
                
                single<TimeApiClient> {
                    SimpleTimeApiClient()
                }
            }
        )
    }
}
