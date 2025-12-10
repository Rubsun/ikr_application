package com.example.ikr_application.dem_module.domain
import com.example.ikr_application.dem_module.data.SampleEntity
import com.example.ikr_application.dem_module.data.SampleRepository
class GetItemsUseCase(
    private val repository: SampleRepository
) {
    suspend operator fun invoke(): List<SampleItem> {
        return repository.getItems().map { it.toDomain() }
    }

    private fun SampleEntity.toDomain(): SampleItem =
        SampleItem(
            id = id,
            title = title,
            description = description
        )
}
