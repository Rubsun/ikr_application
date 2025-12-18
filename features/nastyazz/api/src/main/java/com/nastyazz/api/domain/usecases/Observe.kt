package com.nastyazz.api.domain.usecases

import com.nastyazz.api.domain.models.Item
import kotlinx.coroutines.flow.StateFlow

interface ObserveItemsUseCase {
    operator fun invoke(): StateFlow<List<Item>>
}
