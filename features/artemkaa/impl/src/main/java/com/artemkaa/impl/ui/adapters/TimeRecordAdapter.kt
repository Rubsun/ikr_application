package com.artemkaa.impl.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.artemkaa.api.domain.models.ArtemkaaInfo
import com.artemkaa.impl.R

internal class TimeRecordAdapter : ListAdapter<ArtemkaaInfo, TimeRecordViewHolder>(TimeRecordCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TimeRecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_artemkaa_time_record, parent, false)

        return TimeRecordViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TimeRecordViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    private class TimeRecordCallback: DiffUtil.ItemCallback<ArtemkaaInfo>() {
        override fun areItemsTheSame(
            oldItem: ArtemkaaInfo,
            newItem: ArtemkaaInfo
        ): Boolean {
            return oldItem.currentTime == newItem.currentTime && oldItem.elapsedTime == newItem.elapsedTime
        }

        override fun areContentsTheSame(
            oldItem: ArtemkaaInfo,
            newItem: ArtemkaaInfo
        ): Boolean {
            return oldItem == newItem
        }
    }
}

