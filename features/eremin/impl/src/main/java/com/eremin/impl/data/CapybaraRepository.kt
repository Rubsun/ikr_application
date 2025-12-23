package com.eremin.impl.data

import com.capybara.network.api.CapybaraApiClient
import com.eremin.api.domain.models.Capybara

internal class CapybaraRepository(private val capybaraApiClient: CapybaraApiClient) {

    suspend fun getCapybaras(from: Int, take: Int): List<Capybara> {
        return capybaraApiClient.getCapybaras(from, take).map { Capybara(it.url, it.alt) }
    }
}
