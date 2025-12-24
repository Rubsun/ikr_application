package com.michaelnoskov.impl.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filtered_items")
internal data class FilteredItemEntity(
    @PrimaryKey val id: String,
    val text: String,
    val timestamp: Long,
    val isVisible: Boolean,
    val isSynced: Boolean = false
)

