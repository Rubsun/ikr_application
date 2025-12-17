package com.example.ikr_application.dimmension.domain

import com.example.ikr_application.dimmension.data.NameRepository
import com.example.ikr_application.dimmension.data.models.NameData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddNameUseCase {
    private val repository = NameRepository.INSTANCE

    suspend fun execute(firstName: String, lastName: String) = withContext(Dispatchers.IO) {
        val fullName = "$firstName $lastName"
        val initials = "${firstName.first()}.${lastName.first()}."
        
        val nameData = NameData(
            firstName = firstName,
            lastName = lastName,
            fullName = fullName,
            initials = initials
        )
        
        repository.addName(nameData)
    }
}

