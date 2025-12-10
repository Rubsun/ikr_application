package com.example.ikr_application.denisova.ui

import androidx.lifecycle.ViewModel
import com.example.ikr_application.denisova.domain.CalculateAverageGradeUseCase
import com.example.ikr_application.denisova.domain.GetStudentsUseCase
import com.example.ikr_application.denisova.domain.models.Student

class DenisovaViewModel : ViewModel() {
    private val getStudentsUseCase = GetStudentsUseCase()
    private val calculateAverageGradeUseCase = CalculateAverageGradeUseCase()

    fun students(): List<Student> = getStudentsUseCase.execute()

    fun averageGrade(): Double = calculateAverageGradeUseCase.execute()
}
