package com.example.ikr_application.vtyapkova.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RandomUserApiClient {
    private const val BASE_URL = "https://randomuser.me/"

    val apiService: RandomUserApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RandomUserApiService::class.java)
    }
}

