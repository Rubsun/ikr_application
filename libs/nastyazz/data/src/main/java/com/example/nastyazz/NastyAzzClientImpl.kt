package com.example.nastyazz

import com.example.nastyazz.api.NastyAzzClient
import com.example.nastyazz.api.NastyItem
import com.example.network.api.RetrofitServiceFactory

private const val BASE_URL = "https://dummyjson.com/"

internal class NastyAzzClientImpl(
    retrofitServiceFactory: RetrofitServiceFactory,
) : NastyAzzClient {

    private val service: NastyAzzService = retrofitServiceFactory.create(BASE_URL, NastyAzzService::class.java)

    override suspend fun searchProducts(query: String): List<NastyItem> {
        return service.search(query = query).products.map { dto ->
            NastyItem(
                id = dto.id,
                title = dto.title,
                description = dto.description,
                thumbnail = dto.thumbnail,
            )
        }
    }
}
