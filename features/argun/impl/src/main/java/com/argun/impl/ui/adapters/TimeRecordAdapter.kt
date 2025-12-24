package com.argun.impl.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.argun.api.domain.models.ArgunInfo
import com.argun.impl.R
import com.argun.network.api.TimeFormatter

internal class TimeRecordAdapter(
    private val timeFormatter: TimeFormatter
) : ListAdapter<ArgunInfo, TimeRecordViewHolder>(TimeRecordCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TimeRecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_argun_time_record, parent, false)

        return TimeRecordViewHolder(view, timeFormatter)
    }

    override fun onBindViewHolder(
        holder: TimeRecordViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    private class TimeRecordCallback: DiffUtil.ItemCallback<ArgunInfo>() {
        override fun areItemsTheSame(
            oldItem: ArgunInfo,
            newItem: ArgunInfo
        ): Boolean {
            return oldItem.currentTime == newItem.currentTime && oldItem.elapsedTime == newItem.elapsedTime
        }

        override fun areContentsTheSame(
            oldItem: ArgunInfo,
            newItem: ArgunInfo
        ): Boolean {
            return oldItem == newItem
        }
    }
}

