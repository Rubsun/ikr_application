package com.spl3g.api.domain

import com.spl3g.api.domain.models.FrameResult
import kotlinx.coroutines.flow.Flow

interface GetAppleFramesUseCase {

    fun execute(
        startIndex: Int,
        maxFrames: Int? = null,
        retryOnError: Boolean = false,
        parallelism: Int = 10
    ): Flow<FrameResult>
}