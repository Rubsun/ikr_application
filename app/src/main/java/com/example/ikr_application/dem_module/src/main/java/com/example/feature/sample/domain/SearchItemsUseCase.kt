package com.example.ikr_application.dem_module.domain
import com.example.ikr_application.dem_module.data.SampleRepository
class SearchItemsUseCase(
    private val repository: SampleRepository
) {
    suspend operator fun invoke(query: String): List<SampleItem> {
        if (query.isBlank()) return emptyList()
        return repository.searchItems(query).map {
            SampleItem(
                id = it.id,
                title = it.title,
                description = it.description
            )
        }
    }
}
