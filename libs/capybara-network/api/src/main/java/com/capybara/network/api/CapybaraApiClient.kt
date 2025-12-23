package com.capybara.network.api

import com.capybara.network.api.models.CapybaraData

interface CapybaraApiClient {
    suspend fun getCapybaras(from: Int, take: Int): List<CapybaraData>
}
