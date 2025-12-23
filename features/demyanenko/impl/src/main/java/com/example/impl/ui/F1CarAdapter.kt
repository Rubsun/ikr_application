package com.example.demyanenko.impl.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
//import com.example.demyanenko.impl.R
import com.example.impl.data.F1Car
import com.example.impl.databinding.ItemF1carBinding
import com.example.impl.databinding.ItemDriverBinding
import com.example.libs.demyanenkocoil.DemyanenkoImageLoader
import com.example.libs.demyanenkoopenf1.model.Driver
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


/**
 * Sealed class for mixed items - DEFINED HERE
 */
internal sealed class DemyanenkoItem {
    data class CarItem(val car: F1Car) : DemyanenkoItem()
    data class DriverItem(val driver: Driver) : DemyanenkoItem()
}

internal class F1CarAdapter : ListAdapter<DemyanenkoItem, RecyclerView.ViewHolder>(CarDiffCallback()), KoinComponent {
    private val imageLoader: DemyanenkoImageLoader by inject()

    companion object {
        private const val TYPE_CAR = 0
        private const val TYPE_DRIVER = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DemyanenkoItem.CarItem -> TYPE_CAR
            is DemyanenkoItem.DriverItem -> TYPE_DRIVER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_CAR -> {
                val binding = ItemF1carBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                F1CarViewHolder(binding, imageLoader)
            }
            TYPE_DRIVER -> {
                val binding = ItemDriverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DriverViewHolder(binding, imageLoader)
            }
            else -> throw IllegalArgumentException("Unknown view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is DemyanenkoItem.CarItem -> (holder as F1CarViewHolder).bind(item.car)
            is DemyanenkoItem.DriverItem -> (holder as DriverViewHolder).bind(item.driver)
        }
    }

    // ============ Car ViewHolder ============
    internal class F1CarViewHolder(
        private val binding: ItemF1carBinding,
        private val imageLoader: DemyanenkoImageLoader
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(car: F1Car) {
            binding.apply {
                f1carNameTv.text = car.name
                carSpeedTextView.text = "Макс скорость: ${car.topSpeed} км/ч"
                if (car.sound != null) {
                    phraseChip.text = car.sound
                    phraseChip.visibility = android.view.View.VISIBLE
                } else {
                    phraseChip.visibility = android.view.View.GONE
                }
                if (car.imageRes != null) {
                    imageLoader.load(carImageView, car.imageRes)
                }
            }
        }
    }

    // ============ Driver ViewHolder ============
    internal class DriverViewHolder(
        private val binding: ItemDriverBinding,
        private val imageLoader: DemyanenkoImageLoader
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(driver: Driver) {
            binding.apply {
                driverNameTextView.text = driver.broadcastName ?: driver.fullName ?: "Unknown"
                driverNumberTextView.text = "№${driver.driverNumber}"
                if (!driver.headshotUrl.isNullOrBlank()) {
                    imageLoader.load(driverHeadshotImageView, driver.headshotUrl)
                }
            }
        }
    }
}

internal class CarDiffCallback : DiffUtil.ItemCallback<DemyanenkoItem>() {
    override fun areItemsTheSame(oldItem: DemyanenkoItem, newItem: DemyanenkoItem): Boolean {
        return when {
            oldItem is DemyanenkoItem.CarItem && newItem is DemyanenkoItem.CarItem ->
                oldItem.car.id == newItem.car.id
            oldItem is DemyanenkoItem.DriverItem && newItem is DemyanenkoItem.DriverItem ->
                oldItem.driver.driverNumber == newItem.driver.driverNumber
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: DemyanenkoItem, newItem: DemyanenkoItem): Boolean {
        return oldItem == newItem
    }
}
