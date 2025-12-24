package com.dimmension.impl.domain

import com.dimmension.api.domain.models.NameDisplayModel
import com.dimmension.api.domain.usecases.FetchRandomNamesFromNetworkUseCase
import com.dimmension.impl.data.NameRepository
import com.dimmension.impl.data.remote.RemoteNameDataSource

internal class FetchRandomNamesFromNetworkUseCaseImpl(
    private val remoteDataSource: RemoteNameDataSource,
    private val repository: NameRepository
) : FetchRandomNamesFromNetworkUseCase {

    override suspend fun invoke(count: Int): Result<List<NameDisplayModel>> {
        return remoteDataSource.fetchRandomNames(count)
            .mapCatching { names ->
                // Сохраняем загруженные имена в локальное хранилище
                names.forEach { name ->
                    repository.addName(name.firstName, name.lastName)
                }
                // Преобразуем в доменную модель
                names.map { it.toDomain() }
            }
    }
}

