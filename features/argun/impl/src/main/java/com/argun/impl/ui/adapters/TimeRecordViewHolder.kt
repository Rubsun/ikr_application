package com.argun.impl.ui.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.argun.api.domain.models.ArgunInfo
import com.argun.impl.R
import com.argun.network.api.TimeFormatter

internal class TimeRecordViewHolder(
    view: View,
    private val timeFormatter: TimeFormatter
) : RecyclerView.ViewHolder(view) {
    private val currentTimeText by lazy { view.findViewById<TextView>(R.id.current_time) }
    private val elapsedTimeText by lazy { view.findViewById<TextView>(R.id.elapsed_time) }

    fun bind(item: ArgunInfo) {
        currentTimeText.text = timeFormatter.formatDateTime(item.currentTime)
        elapsedTimeText.text = timeFormatter.formatElapsedTime(item.elapsedTime)
    }
}

