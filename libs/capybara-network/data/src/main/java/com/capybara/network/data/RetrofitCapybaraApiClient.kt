package com.capybara.network.data

import com.capybara.network.api.CapybaraApiClient
import com.capybara.network.api.models.CapybaraData
import com.example.network.api.RetrofitServiceFactory

private const val BASE_URL = "https://api.capy.lol/"

internal class RetrofitCapybaraApiClient(
    retrofitServiceFactory: RetrofitServiceFactory
) : CapybaraApiClient {

    private val capybaraService = retrofitServiceFactory.create(BASE_URL, CapybaraService::class.java)

    override suspend fun getCapybaras(from: Int, take: Int): List<CapybaraData> {
        return capybaraService.getCapybaras(from, take).data
    }
}
