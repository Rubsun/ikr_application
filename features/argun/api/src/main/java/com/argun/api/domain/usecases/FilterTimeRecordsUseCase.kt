package com.argun.api.domain.usecases

import com.argun.api.domain.models.ArgunInfo
import kotlinx.coroutines.flow.Flow

interface FilterTimeRecordsUseCase {
    fun invoke(query: String = ""): Flow<List<ArgunInfo>>
}

