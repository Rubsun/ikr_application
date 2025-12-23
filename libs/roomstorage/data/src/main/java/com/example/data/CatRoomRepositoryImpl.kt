package com.example.data

import com.example.api.CatRoomRepository
import com.example.data.db.CatDao
import com.example.data.db.CatEntity
import com.example.data.mapper.toDtoList
import com.example.api.CatDto
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