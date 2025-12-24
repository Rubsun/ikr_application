package com.michaelnoskov.impl.data.mapper

import com.michaelnoskov.api.domain.model.ChartData
import com.michaelnoskov.api.domain.model.FilteredItem
import com.michaelnoskov.api.domain.model.SquareData
import com.michaelnoskov.impl.data.datasource.DataSourceFilteredItemEntity
import com.michaelnoskov.impl.data.datasource.DataSourceSquareEntity

internal class DataMapper {

    fun mapToSquareData(entity: DataSourceSquareEntity): SquareData {
        return SquareData(
            id = entity.id,
            color = entity.color,
            size = entity.size,
            rotation = entity.rotation,
            alpha = entity.alpha
        )
    }

    fun mapToDataSourceSquareEntity(data: SquareData): DataSourceSquareEntity {
        return DataSourceSquareEntity(
            id = data.id,
            color = data.color,
            size = data.size,
            rotation = data.rotation,
            alpha = data.alpha,
            updatedAt = System.currentTimeMillis()
        )
    }

    fun mapToFilteredItem(entity: DataSourceFilteredItemEntity): FilteredItem {
        return FilteredItem(
            id = entity.id,
            text = entity.text,
            timestamp = entity.timestamp,
            isVisible = entity.isVisible
        )
    }

    fun mapToFilteredItemEntity(item: FilteredItem): DataSourceFilteredItemEntity {
        return DataSourceFilteredItemEntity(
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

