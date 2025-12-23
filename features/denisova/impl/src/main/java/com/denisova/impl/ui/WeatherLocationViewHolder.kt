package com.denisova.impl.ui

import android.view.View
import android.widget.TextView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.denisova.api.domain.models.WeatherLocation
import com.denisova.impl.R
import com.imageloader.api.ImageLoader
import java.net.URLEncoder

internal class WeatherLocationViewHolder(
    view: View,
    private val imageLoader: ImageLoader,
) : RecyclerView.ViewHolder(view) {

    private val titleView: TextView by lazy { itemView.findViewById<TextView>(R.id.title) }
    private val subtitleView: TextView by lazy { itemView.findViewById<TextView>(R.id.subtitle) }
    private val coordsView: TextView by lazy { itemView.findViewById<TextView>(R.id.coords) }
    private val temperatureView: TextView by lazy { itemView.findViewById<TextView>(R.id.temperature) }
    private val timeView: TextView by lazy { itemView.findViewById<TextView>(R.id.time) }
    private val sparklineView: TemperatureSparklineView by lazy { itemView.findViewById<TemperatureSparklineView>(R.id.sparkline) }
    private val avatarView: ImageView by lazy { itemView.findViewById<ImageView>(R.id.avatar) }

    fun bind(item: WeatherLocation?) {
        when {
            item == null -> {
                titleView.text = null
                subtitleView.text = null
                coordsView.text = null
                temperatureView.text = null
                timeView.text = null
                sparklineView.setTemperaturesC(emptyList())
                avatarView.setImageDrawable(null)
            }
            else -> {
                titleView.text = item.name
                subtitleView.text = buildString {
                    if (!item.admin1.isNullOrBlank()) append(item.admin1)
                    if (!item.country.isNullOrBlank()) {
                        if (isNotEmpty()) append(", ")
                        append(item.country)
                    }
                }

                coordsView.text = itemView.context.getString(
                    R.string.denisova_weather_coords_pattern,
                    item.latitude,
                    item.longitude,
                )

                temperatureView.text = if (item.temperatureC.isNaN()) {
                    itemView.context.getString(R.string.denisova_weather_temp_unknown)
                } else {
                    itemView.context.getString(R.string.denisova_weather_temp_pattern, item.temperatureC)
                }

                timeView.text = if (item.time.isBlank()) {
                    itemView.context.getString(R.string.denisova_weather_time_unknown)
                } else {
                    itemView.context.getString(R.string.denisova_weather_time_pattern, item.time)
                }

                sparklineView.setTemperaturesC(item.hourlyTemperaturesC)

                val seed = URLEncoder.encode(item.name, "UTF-8")
                val url = "https://api.dicebear.com/8.x/initials/png?seed=$seed&radius=50&size=64"
                imageLoader.load(avatarView, url)
            }
        }
    }
}
