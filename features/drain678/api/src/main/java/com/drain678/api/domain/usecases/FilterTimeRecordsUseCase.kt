package com.drain678.api.domain.usecases

import com.drain678.api.domain.models.Drain678Info
import kotlinx.coroutines.flow.Flow

interface FilterTimeRecordsUseCase {
    fun invoke(query: String = ""): Flow<List<Drain678Info>>
}

