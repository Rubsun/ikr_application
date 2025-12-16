package com.example.ikr_application.drain678.ui.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ikr_application.R
import com.example.ikr_application.drain678.data.model.Drain678Info
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.format.DateTimeFormat

class TimeRecordViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    private val currentTimeText by lazy { view.findViewById<TextView>(R.id.current_time) }
    private val elapsedTimeText by lazy { view.findViewById<TextView>(R.id.elapsed_time) }
    private val dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

    fun bind(item: Drain678Info) {
        val dateTime = DateTime(item.currentTime)
        currentTimeText.text = dateTimeFormatter.print(dateTime)
        elapsedTimeText.text = formatElapsedTime(item.elapsedTime)
    }

    private fun formatElapsedTime(elapsedTime: Long): String {
        val duration = Duration(elapsedTime)
        val hours = duration.standardHours
        val minutes = duration.standardMinutes % 60
        val seconds = duration.standardSeconds % 60
        val milliseconds = duration.millis % 1000 / 100
        return String.format("%02d:%02d:%02d.%01d", hours, minutes, seconds, milliseconds)
    }
}

