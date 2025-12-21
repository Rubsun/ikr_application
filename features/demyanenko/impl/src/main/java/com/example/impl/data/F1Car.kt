package com.example.impl.data

import androidx.annotation.DrawableRes
import java.util.UUID

internal data class F1Car(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val sound: String? = null,
    @DrawableRes val imageRes: Int? = null,
    val topSpeed: Int = 300
)
