package com.example.ikr_application.denisova.domain

import com.example.ikr_application.denisova.data.StudentsRepository

class CalculateAverageGradeUseCase(
    private val repository: StudentsRepository = StudentsRepository()
) {
    fun execute(): Double {
        val students = repository.getStudents()
        return if (students.isEmpty()) 0.0 else students.map { it.grade }.average()
    }
}
