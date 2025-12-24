package quo.vadis.impl.domain

import com.example.api.CatNameRepository
import com.example.injector.inject
import quo.vadis.api.model.Cat
import quo.vadis.api.usecases.GetCatNameUseCase

internal class GetCatNameUseCaseImpl : GetCatNameUseCase {
    private val repository: CatNameRepository by inject()

    override suspend fun getRandomCat(phrase: String?): Cat {
        return Cat(name = repository.getCatName(), phrase = phrase)
    }
}