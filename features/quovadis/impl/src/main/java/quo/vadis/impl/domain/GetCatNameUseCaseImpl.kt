package quo.vadis.impl.domain

import quo.vadis.api.model.Cat
import quo.vadis.api.usecases.GetCatNameUseCase
import quo.vadis.impl.data.CatRepositoryImpl

internal class GetCatNameUseCaseImpl (
    private val repository: CatRepositoryImpl
) : GetCatNameUseCase {
    override fun getRandomCat(phrase: String?): Cat {
        return repository.getCat(phrase)
    }
}