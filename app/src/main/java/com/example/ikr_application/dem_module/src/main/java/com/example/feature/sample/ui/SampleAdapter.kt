package com.example.ikr_application.dem_module.ui
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ikr_application.databinding.ItemSampleBinding
import com.example.ikr_application.dem_module.domain.SampleItem

class SampleAdapter(
    private val onItemClick: (SampleItem) -> Unit = {}
) : ListAdapter<SampleItem, SampleAdapter.SampleViewHolder>(SampleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        val binding = com.example.feature.sample.databinding.ItemSampleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SampleViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SampleViewHolder(
        private val binding: com.example.feature.sample.databinding.ItemSampleBinding,
        private val onItemClick: (SampleItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SampleItem) {
            binding.apply {
                com.example.feature.sample.databinding.ItemSampleBinding.tvTitle.text = item.title
                com.example.feature.sample.databinding.ItemSampleBinding.tvDescription.text = item.description
                com.example.feature.sample.databinding.ItemSampleBinding.tvId.text = "ID: ${item.id}"

                com.example.feature.sample.databinding.ItemSampleBinding.getRoot.setOnClickListener {
                    onItemClick(item)
                }
            }
        }
    }

    private class SampleDiffCallback : DiffUtil.ItemCallback<SampleItem>() {
        override fun areItemsTheSame(oldItem: SampleItem, newItem: SampleItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SampleItem, newItem: SampleItem) =
            oldItem == newItem
    }
}
