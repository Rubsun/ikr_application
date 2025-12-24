package com.tire.api.domain.usecases

import com.tire.api.domain.models.CaseOpenResult
import kotlinx.coroutines.flow.Flow

interface OpenCaseUseCase {
    suspend operator fun invoke(caseId: String): Flow<CaseOpenResult>
}
