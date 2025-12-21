package com.grigoran.api.domain

import com.grigoran.api.models.Item

interface SortItemUseCase {
    operator fun invoke(items: List<Item>, ascending: Boolean): List<Item>
}