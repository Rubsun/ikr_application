package quo.vadis.impl.data

import kotlinx.coroutines.flow.Flow
import quo.vadis.api.model.Cat

internal class CatRepositoryImpl {
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
}