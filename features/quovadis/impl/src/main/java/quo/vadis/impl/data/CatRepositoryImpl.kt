package quo.vadis.impl.data

import kotlinx.coroutines.flow.Flow
import quo.vadis.api.model.Cat
import quo.vadis.impl.data.db.CatDao
import quo.vadis.impl.data.db.CatEntity

internal class CatRepositoryImpl(
    private val catDao: CatDao
) {
    private val catNames = listOf(
        "Барсик", "Мурзик", "Пушок", "Семён",
        "Васька", "Леопольд", "Федя", "Тимон",
        "Гаврюша", "Тихон", "Матвей", "Кузя",
        "Прошка", "Емеля", "Мишка", "Снежок",
        "Рыжик", "Шурик", "Герасим", "Платон"
    )

    fun getCat(phrase: String?): Cat {
        return Cat(name = catNames.random(), phrase = phrase)
    }

    fun getAllCatsFlow(): Flow<List<CatEntity>> {
        return catDao.getAllCatsFlow()
    }

    suspend fun insertCat(
        name: String,
        phrase: String?,
        imageUrl: String?
    ): Long {
        val cat = CatEntity(
            name = name,
            phrase = phrase,
            imageUrl = imageUrl
        )
        return catDao.insertCat(cat)
    }

    suspend fun deleteCat(catId: Long) {
        catDao.deleteCatById(catId)
    }

    suspend fun deleteAllCats() {
        catDao.deleteAllCats()
    }
}