package com.example.ikr_application.n0tsSzzz.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.ikr_application.R
import com.example.ikr_application.n0tsSzzz.data.models.MarkoInfo

internal class TimeRecordAdapter : ListAdapter<MarkoInfo, TimeRecordViewHolder>(TimeRecordCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TimeRecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_n0tsszzz_record, parent, false)
        return TimeRecordViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TimeRecordViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    private class TimeRecordCallback : DiffUtil.ItemCallback<MarkoInfo>() {
        override fun areItemsTheSame(
            oldItem: MarkoInfo,
            newItem: MarkoInfo
        ): Boolean {
            return oldItem.currentTime == newItem.currentTime && oldItem.elapsedTime == newItem.elapsedTime
        }

        override fun areContentsTheSame(
            oldItem: MarkoInfo,
            newItem: MarkoInfo
        ): Boolean {
            return oldItem.currentTime == newItem.currentTime && oldItem.elapsedTime == newItem.elapsedTime
        }
    }
}
