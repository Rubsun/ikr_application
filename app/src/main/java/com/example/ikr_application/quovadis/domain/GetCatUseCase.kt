package quo.vadis.sirius.domain

import quo.vadis.sirius.data.Cat
import quo.vadis.sirius.data.CatRepository

class GetCatUseCase(
    private val repository: CatRepository
) {
    fun getRandomCat(text: String?): Cat {
        return repository.getCat(text)
    }
}