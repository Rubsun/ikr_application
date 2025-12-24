package com.example.ikr_application.michaelnoskov.data.mapper

import com.example.ikr_application.michaelnoskov.data.local.FilteredItemEntity
import com.example.ikr_application.michaelnoskov.data.local.SquareEntity
import com.example.ikr_application.michaelnoskov.domain.model.ChartData
import com.example.ikr_application.michaelnoskov.domain.model.FilteredItem
import com.example.ikr_application.michaelnoskov.domain.model.SquareData

class DataMapper {

    fun mapToSquareData(entity: SquareEntity): SquareData {
        return SquareData(
            id = entity.id,
            color = entity.color,
            size = entity.size,
            rotation = entity.rotation,
            alpha = entity.alpha
        )
    }

    fun mapToSquareEntity(data: SquareData): SquareEntity {
        return SquareEntity(
            id = data.id,
            color = data.color,
            size = data.size,
            rotation = data.rotation,
            alpha = data.alpha,
            updatedAt = System.currentTimeMillis()
        )
    }

    fun mapToFilteredItem(entity: FilteredItemEntity): FilteredItem {
        return FilteredItem(
            id = entity.id,
            text = entity.text,
            timestamp = entity.timestamp,
            isVisible = entity.isVisible
        )
    }

    fun mapToFilteredItemEntity(item: FilteredItem): FilteredItemEntity {
        return FilteredItemEntity(
            id = item.id,
            text = item.text,
            timestamp = item.timestamp,
            isVisible = item.isVisible,
            isSynced = false
        )
    }

    fun mapToChartData(label: String, value: Float, color: Int): ChartData {
        return ChartData(label = label, value = value, color = color)
    }
}