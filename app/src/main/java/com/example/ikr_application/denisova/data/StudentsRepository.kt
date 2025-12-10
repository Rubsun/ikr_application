package com.example.ikr_application.denisova.data

import com.example.ikr_application.denisova.data.models.StudentDto

class StudentsRepository {
    fun getStudents(): List<StudentDto> {
        return listOf(
            StudentDto(name = "Arina Denisova", grade = 5),
            StudentDto(name = "Daniil Ivanov", grade = 4),
            StudentDto(name = "Kate Petrov", grade = 3),
            StudentDto(name = "Daria Sidorova", grade = 5),
        )
    }
}
