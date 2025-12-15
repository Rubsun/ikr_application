package com.example.ikr_application.denisova.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.ikr_application.R
import com.example.ikr_application.denisova.domain.models.WeatherLocation

class WeatherLocationAdapter : ListAdapter<WeatherLocation, WeatherLocationViewHolder>(WeatherLocationDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherLocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_denisova_weather, parent, false)
        return WeatherLocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherLocationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class WeatherLocationDiff : DiffUtil.ItemCallback<WeatherLocation>() {
        override fun areItemsTheSame(oldItem: WeatherLocation, newItem: WeatherLocation): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: WeatherLocation, newItem: WeatherLocation): Boolean = oldItem == newItem
    }
}
