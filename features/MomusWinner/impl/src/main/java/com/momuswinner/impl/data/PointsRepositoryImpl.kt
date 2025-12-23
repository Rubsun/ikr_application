package com.momuswinner.impl.data

import com.example.primitivestorage.api.PrimitiveStorage
import com.momuswinner.api.data.PointsRepository
import com.momuswinner.api.domain.models.Point
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.builtins.ListSerializer

private const val STORAGE_NAME = "momus_winner_points.json"

internal class PointsRepositoryImpl(
    storageFactory: PrimitiveStorage.Factory,
) : PointsRepository {

    private val storage = storageFactory.create(
        name = STORAGE_NAME,
        serializer = ListSerializer(PointDto.serializer()),
    )

    override fun getAllPoints(): Flow<List<Point>> = storage.get().map { list ->
        list.orEmpty().map { dto -> Point(dto.x, dto.y) }
    }

    override suspend fun addPoint(point: Point) {
        val currentPoints = storage.get().first().orEmpty().toMutableList()
        currentPoints.add(point.toDto())
        storage.put(currentPoints)
    }

    override suspend fun clearPoints() {
        storage.put(emptyList())
    }

    private fun Point.toDto() = PointDto(x, y)
}
