package com.michaelnoskov.impl.data.mapper

import android.graphics.Color
import com.michaelnoskov.api.domain.model.ChartData
import com.michaelnoskov.api.domain.model.FilteredItem
import com.michaelnoskov.impl.data.api.model.ChartDataDto
import com.michaelnoskov.impl.data.api.model.LocalItemDto
import com.michaelnoskov.impl.data.api.model.RemoteItemResponse
import java.text.SimpleDateFormat
import java.util.Locale

internal class NetworkMapper {

    fun mapChartData(dtos: List<ChartDataDto>): List<ChartData> {
        return dtos.map { dto ->
            ChartData(
                label = dto.label,
                value = dto.value,
                color = try {
                    Color.parseColor(dto.colorHex)
                } catch (e: Exception) {
                    Color.BLACK
                }
            )
        }
    }

    fun mapRemoteItem(response: RemoteItemResponse): FilteredItem {
        return FilteredItem(
            id = response.id,
            text = response.text,
            timestamp = parseDate(response.createdAt),
            isVisible = true
        )
    }

    fun mapToLocalItemDto(item: FilteredItem): LocalItemDto {
        return LocalItemDto(
            id = item.id,
            text = item.text,
            timestamp = item.timestamp
        )
    }

    private fun parseDate(dateString: String): Long {
        return try {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            formatter.parse(dateString)?.time ?: System.currentTimeMillis()
        } catch (e: Exception) {
            System.currentTimeMillis()
        }
    }
}

