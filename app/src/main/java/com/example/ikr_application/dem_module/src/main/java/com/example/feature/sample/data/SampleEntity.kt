package com.example.ikr_application.dem_module.data
data class SampleEntity(
    val id: Int,
    val title: String,
    val description: String,
    val timestamp: Long = System.currentTimeMillis()
)
