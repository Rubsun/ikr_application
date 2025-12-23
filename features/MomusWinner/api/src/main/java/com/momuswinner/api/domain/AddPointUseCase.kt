package com.momuswinner.api.domain

import com.momuswinner.api.domain.models.Point
import kotlinx.coroutines.flow.Flow

interface AddPointUseCase {
    suspend fun execute(point: Point)
    fun getPoints(): Flow<List<Point>>
    suspend fun clearPoints()
}
