package com.rubsun.storage.data

import com.rubsun.storage.api.NumberStorage
import com.rubsun.storage.api.models.NumberEntity
import com.rubsun.storage.data.database.RoomNumberDao
import com.rubsun.storage.data.database.RoomNumberEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RoomNumberStorage(
    private val roomNumberDao: RoomNumberDao
) : NumberStorage {
    override fun getAllNumbers(): Flow<List<NumberEntity>> {
        return roomNumberDao.getAllNumbers().map { entities ->
            entities.map { it.toNumberEntity() }
        }
    }

    override suspend fun getRandomNumber(): NumberEntity? {
        return roomNumberDao.getRandomNumber()?.toNumberEntity()
    }

    override suspend fun getCount(): Int {
        return roomNumberDao.getCount()
    }

    override suspend fun insertNumber(number: NumberEntity): Long {
        return roomNumberDao.insertNumber(number.toRoomEntity())
    }

    override suspend fun deleteNumber(id: Long) {
        roomNumberDao.deleteNumber(id)
    }

    override suspend fun deleteAllNumbers() {
        roomNumberDao.deleteAllNumbers()
    }

    private fun RoomNumberEntity.toNumberEntity(): NumberEntity {
        return NumberEntity(
            id = id,
            value = value,
            label = label
        )
    }

    private fun NumberEntity.toRoomEntity(): RoomNumberEntity {
        return RoomNumberEntity(
            id = id,
            value = value,
            label = label
        )
    }
}


