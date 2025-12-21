package com.grigoran.api.domain

import com.grigoran.api.models.Item

interface FilterItemUseCase {
    operator fun invoke(items: List<Item>, minPrice: Double): List<Item>
}