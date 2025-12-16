package com.example.ikr_application.demyanenko.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ikr_application.databinding.ItemF1carBinding
import com.example.ikr_application.demyanenko.data.F1Car

class F1CarAdapter : ListAdapter<F1Car, F1CarAdapter.F1CarViewHolder>(CarDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): F1CarViewHolder {
        val binding = ItemF1carBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return F1CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: F1CarViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class F1CarViewHolder(private val binding: ItemF1carBinding) :
        RecyclerView.ViewHolder(binding.root) {

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
                    carImageView.setImageResource(car.imageRes)
                } else {
                    carImageView.setImageResource(android.R.drawable.ic_menu_gallery)
                }
            }
        }
    }
}

class CarDiffCallback : DiffUtil.ItemCallback<F1Car>() {
    override fun areItemsTheSame(oldItem: F1Car, newItem: F1Car) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: F1Car, newItem: F1Car) =
        oldItem == newItem
}
