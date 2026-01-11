package com.roomstorage.data

import com.example.api.CatDto
import com.example.api.CatRoomRepository
import com.roomstorage.data.db.CatDao
import com.roomstorage.data.db.CatEntity
import com.roomstorage.data.mapper.toDtoList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CatRoomRepositoryImpl(
    private val catDao: CatDao
) : CatRoomRepository {
    override fun getAllCatsFlow(): Flow<List<CatDto>> {
        return catDao.getAllCatsFlow().map{ it.toDtoList() }
    }

    override suspend fun insertCat(
        name: String,
        phrase: String?,
        imageUrl: String?,
        fetchedFrom: String
    ): Long {
        val cat = CatEntity(
            name = name,
            phrase = phrase,
            imageUrl = imageUrl,
            fetchedFrom = fetchedFrom
        )
        return catDao.insertCat(cat)
    }

    override suspend fun deleteCat(catId: Long) {
        catDao.deleteCatById(catId)
    }

    override suspend fun deleteAllCats() {
        catDao.deleteAllCats()
    }
}