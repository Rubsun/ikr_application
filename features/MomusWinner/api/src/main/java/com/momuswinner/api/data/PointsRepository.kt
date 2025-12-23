package com.momuswinner.api.data

import com.momuswinner.api.domain.models.Point
import kotlinx.coroutines.flow.Flow

interface PointsRepository {
    fun getAllPoints(): Flow<List<Point>>
    suspend fun addPoint(point: Point)
    suspend fun clearPoints()
}
