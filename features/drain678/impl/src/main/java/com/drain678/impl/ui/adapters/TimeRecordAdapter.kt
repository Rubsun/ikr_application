package com.drain678.impl.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.drain678.api.domain.models.Drain678Info
import com.drain678.impl.R

internal class TimeRecordAdapter : androidx.recyclerview.widget.ListAdapter<Drain678Info, TimeRecordViewHolder>(TimeRecordCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TimeRecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_drain678_time_record, parent, false)

        return TimeRecordViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TimeRecordViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    private class TimeRecordCallback: DiffUtil.ItemCallback<Drain678Info>() {
        override fun areItemsTheSame(
            oldItem: Drain678Info,
            newItem: Drain678Info
        ): Boolean {
            return oldItem.currentTime == newItem.currentTime && oldItem.elapsedTime == newItem.elapsedTime
        }

        override fun areContentsTheSame(
            oldItem: Drain678Info,
            newItem: Drain678Info
        ): Boolean {
            return oldItem == newItem
        }
    }
}

