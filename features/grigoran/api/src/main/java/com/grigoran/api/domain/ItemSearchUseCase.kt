package com.grigoran.api.domain

import com.grigoran.api.models.ItemResult

interface ItemSearchUseCase {
    suspend operator fun invoke(query: String): ItemResult
}