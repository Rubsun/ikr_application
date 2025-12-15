package com.example.ikr_application.n0tsSzzz.ui.adapters

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ikr_application.R
import com.example.ikr_application.n0tsSzzz.data.models.MarkoInfo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimeRecordViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    private val currentTime by lazy { view.findViewById<TextView>(R.id.current_time) }
    private val elapsedTime by lazy { view.findViewById<TextView>(R.id.elapsed_time) }
    
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    @SuppressLint("SetTextI18n")
    fun bind(item: MarkoInfo) {
        currentTime.text = "Current Time: ${dateFormat.format(Date(item.currentTime))}"
        elapsedTime.text = "Elapsed Time: ${item.elapsedTime}ms"
    }
}
