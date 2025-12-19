package com.example.ikr_application.drain678.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.ikr_application.R
import com.example.ikr_application.drain678.data.model.Drain678Info

internal class TimeRecordAdapter : ListAdapter<Drain678Info, com.example.ikr_application.drain678.ui.adapters.TimeRecordViewHolder>(TimeRecordCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): com.example.ikr_application.drain678.ui.adapters.TimeRecordViewHolder {
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

