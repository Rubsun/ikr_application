package com.dimmension.api.domain.usecases

import com.dimmension.api.domain.models.NameDisplayModel

/**
 * Use case для загрузки случайных имён из интернета
 */
interface FetchRandomNamesFromNetworkUseCase {
    /**
     * Загружает случайные имена из сети и сохраняет их
     * @param count количество имён для загрузки
     * @return Result со списком загруженных имён или ошибкой
     */
    suspend operator fun invoke(count: Int = 5): Result<List<NameDisplayModel>>
}

