package com.n0tsszzz.api.domain.usecases

import com.n0tsszzz.api.domain.models.MarkoInfo
import kotlinx.coroutines.flow.Flow

interface GetTimeRecordsUseCase {
    fun invoke(query: String = ""): Flow<List<MarkoInfo>>
}

