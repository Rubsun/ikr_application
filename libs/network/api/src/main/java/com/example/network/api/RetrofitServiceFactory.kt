package com.example.network.api

interface RetrofitServiceFactory {
    fun <T> create(baseUrl: String, service: Class<T>): T
}
