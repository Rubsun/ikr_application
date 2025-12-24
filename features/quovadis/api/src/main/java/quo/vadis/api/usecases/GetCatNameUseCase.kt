package quo.vadis.api.usecases

import quo.vadis.api.model.Cat

interface GetCatNameUseCase {
    suspend fun getRandomCat(phrase: String?): Cat
}