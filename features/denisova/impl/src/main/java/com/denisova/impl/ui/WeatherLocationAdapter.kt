package com.denisova.impl.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.denisova.api.domain.models.WeatherLocation
import com.denisova.impl.R
import com.imageloader.api.ImageLoader

internal class WeatherLocationAdapter(
    private val imageLoader: ImageLoader,
) : ListAdapter<WeatherLocation, WeatherLocationViewHolder>(WeatherLocationDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherLocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_denisova_weather, parent, false)
        return WeatherLocationViewHolder(view, imageLoader)
    }

    override fun onBindViewHolder(holder: WeatherLocationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class WeatherLocationDiff : DiffUtil.ItemCallback<WeatherLocation>() {
        override fun areItemsTheSame(oldItem: WeatherLocation, newItem: WeatherLocation): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: WeatherLocation, newItem: WeatherLocation): Boolean = oldItem == newItem
    }
}
