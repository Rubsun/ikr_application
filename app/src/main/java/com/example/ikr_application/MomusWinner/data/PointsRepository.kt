package com.example.pointsgraph.data.repository

import com.example.ikr_application.MomusWinner.data.models.Point
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface PointsRepository {
    fun getAllPoints(): Flow<List<Point>>
    suspend fun addPoint(point: Point)
    suspend fun clearPoints()
}

class PointsRepositoryImpl : PointsRepository {
    private val _points = MutableStateFlow<List<Point>>(
        listOf(
            Point(1.0, 2.0),
            Point(2.0, 4.0),
            Point(3.0, 1.0),
            Point(4.0, 3.0),
            Point(5.0, 5.0)
        )
    )

    override fun getAllPoints(): Flow<List<Point>> = _points.asStateFlow()

    override suspend fun addPoint(point: Point) {
        val currentPoints = _points.value.toMutableList()
        currentPoints.add(point)
        _points.value = currentPoints
    }

    override suspend fun clearPoints() {
        _points.value = emptyList()
    }
}
