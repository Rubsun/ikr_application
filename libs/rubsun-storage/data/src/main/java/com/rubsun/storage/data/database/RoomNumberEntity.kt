package com.rubsun.storage.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "numbers")
internal data class RoomNumberEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val value: Int,
    val label: String,
)


