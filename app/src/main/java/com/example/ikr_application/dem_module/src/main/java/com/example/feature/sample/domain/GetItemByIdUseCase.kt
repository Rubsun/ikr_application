package com.example.ikr_application.dem_module.domain
import com.example.ikr_application.dem_module.data.SampleRepository
class GetItemByIdUseCase(
    private val repository: SampleRepository
) {
    suspend operator fun invoke(id: Int): SampleItem? {
        return repository.getItemById(id)?.let {
            SampleItem(
                id = it.id,
                title = it.title,
                description = it.description
            )
        }
    }
}
