package com.example.ikr_application.MomusWinner.domain

import com.example.ikr_application.MomusWinner.data.models.Point
import com.example.pointsgraph.data.repository.PointsRepository
import kotlinx.coroutines.flow.Flow


interface AddPointUseCase {
    suspend fun execute(point: Point)
    fun getPoints(): Flow<List<Point>>
    suspend fun clearPoints()
}

class AddPointUseCaseImpl(
    private val repository: PointsRepository
) : AddPointUseCase {
    override suspend fun execute(point: Point) {
        repository.addPoint(point)
    }

    override fun getPoints(): Flow<List<Point>> = repository.getAllPoints()

    override suspend fun clearPoints() {
        repository.clearPoints()
    }
}
