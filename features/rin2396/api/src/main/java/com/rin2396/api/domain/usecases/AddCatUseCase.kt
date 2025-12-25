package com.rin2396.api.domain.usecases

import kotlinx.coroutines.flow.Flow

interface AddCatUseCase {
    fun execute(tag: String = "random"): Flow<Unit>
}
