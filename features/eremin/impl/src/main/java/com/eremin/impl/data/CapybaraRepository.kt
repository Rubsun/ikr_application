package com.eremin.impl.data

import com.eremin.impl.data.models.CapybaraData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

internal class CapybaraRepository {

    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.capy.lol/")
        .client(okHttpClient)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val capybaraApi = retrofit.create(CapybaraApi::class.java)

    suspend fun getCapybaras(from: Int, take: Int): List<CapybaraData> {
        return capybaraApi.getCapybaras(from, take).data
    }
}
