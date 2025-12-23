package com.momuswinner.impl.domain

import com.momuswinner.api.data.PointsRepository
import com.momuswinner.api.domain.AddPointUseCase
import com.momuswinner.api.domain.models.Point
import kotlinx.coroutines.flow.Flow

internal class AddPointUseCaseImpl(
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
