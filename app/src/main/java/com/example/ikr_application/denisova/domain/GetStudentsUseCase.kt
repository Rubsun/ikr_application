package com.example.ikr_application.denisova.domain

import com.example.ikr_application.denisova.data.StudentsRepository
import com.example.ikr_application.denisova.domain.models.Student

class GetStudentsUseCase(
    private val repository: StudentsRepository = StudentsRepository()
) {
    fun execute(): List<Student> {
        return repository.getStudents().map { dto ->
            Student(fullName = dto.name, grade = dto.grade)
        }
    }
}
