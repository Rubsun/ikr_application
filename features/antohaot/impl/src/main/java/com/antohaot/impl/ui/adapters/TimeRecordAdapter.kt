package com.antohaot.impl.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.antohaot.api.domain.models.AntohaotInfo
import com.antohaot.impl.R

internal class TimeRecordAdapter : ListAdapter<AntohaotInfo, TimeRecordViewHolder>(TimeRecordCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TimeRecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_antohaot_time_record, parent, false)

        return TimeRecordViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TimeRecordViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    private class TimeRecordCallback: DiffUtil.ItemCallback<AntohaotInfo>() {
        override fun areItemsTheSame(
            oldItem: AntohaotInfo,
            newItem: AntohaotInfo
        ): Boolean {
            return oldItem.currentTime == newItem.currentTime && oldItem.elapsedTime == newItem.elapsedTime
        }

        override fun areContentsTheSame(
            oldItem: AntohaotInfo,
            newItem: AntohaotInfo
        ): Boolean {
            return oldItem == newItem
        }
    }
}

