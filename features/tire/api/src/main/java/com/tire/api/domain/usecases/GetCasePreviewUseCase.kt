package com.tire.api.domain.usecases

import com.tire.api.domain.models.CasePreview
import kotlinx.coroutines.flow.Flow

interface GetCasePreviewUseCase {
    suspend operator fun invoke(caseId: String): Flow<CasePreview>
}

