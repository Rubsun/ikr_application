package com.example.libs.demyanenkoopenf1

import android.content.Context
import com.example.injector.AbstractInitializer
import com.example.libs.demyanenkoopenf1.api.OpenF1Api
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Initializes the Demyanenko OpenF1 module with Koin DI.
 * Auto-loaded via androidx.startup.InitializationProvider.
 */
internal class DemyanenkoOpenF1Module : AbstractInitializer<Unit>() {
    override fun create(context: Context) {
        loadKoinModules(
            module {
                // OkHttp client
                single {
                    OkHttpClient.Builder()
                        .build()
                }

                // Retrofit instance
                single {
                    val okHttpClient = get<OkHttpClient>()
                    val json = Json { ignoreUnknownKeys = true }

                    Retrofit.Builder()
                        .baseUrl("https://api.openf1.org/")
                        .client(okHttpClient)
                        .addConverterFactory(
                            json.asConverterFactory("application/json".toMediaType())
                        )
                        .build()
                }

                // OpenF1Api service
                single {
                    val retrofit = get<Retrofit>()
                    retrofit.create(OpenF1Api::class.java)
                }

                // Repository
                single<DemyanenkoOpenF1Repository> {
                    RetrofitOpenF1Repository(get()) as DemyanenkoOpenF1Repository
                }
            }
        )
    }
}
