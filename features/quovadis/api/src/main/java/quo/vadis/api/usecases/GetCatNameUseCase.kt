package quo.vadis.api.usecases

import quo.vadis.api.model.Cat

interface GetCatNameUseCase {
    fun getRandomCat(phrase: String?): Cat
}