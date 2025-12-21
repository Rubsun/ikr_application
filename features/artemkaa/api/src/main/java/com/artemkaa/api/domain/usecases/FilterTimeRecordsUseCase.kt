package com.artemkaa.api.domain.usecases

import com.artemkaa.api.domain.models.ArtemkaaInfo
import kotlinx.coroutines.flow.Flow

interface FilterTimeRecordsUseCase {
    fun invoke(query: String = ""): Flow<List<ArtemkaaInfo>>
}

