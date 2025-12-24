package com.example.nastyazz.api

interface NastyAzzClient {
    suspend fun searchProducts(query: String): List<NastyItem>
}
