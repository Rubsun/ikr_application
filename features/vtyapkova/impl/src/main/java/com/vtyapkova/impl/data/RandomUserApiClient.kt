package com.vtyapkova.impl.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object RandomUserApiClient {
    private const val BASE_URL = "https://randomuser.me/"

    fun create(): RandomUserApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RandomUserApiService::class.java)
    }
}
