package com.example.ikr_application.demyanenko.data

import androidx.annotation.DrawableRes

data class F1Car(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val sound: String? = null,
    @DrawableRes val imageRes: Int? = null,
    val topSpeed: Int = 300
)
