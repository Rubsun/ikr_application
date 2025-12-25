package com.grigoran.network.api

interface ApiClient {
    suspend fun search(query: String): List<Item>
}