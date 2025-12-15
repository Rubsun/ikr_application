package com.example.ikr_application.nastyazz.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ikr_application.databinding.ItemNastyazzBinding
import com.example.ikr_application.nastyazz.domain.Item

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.VH>() {

    private val data = mutableListOf<Item>()

    fun submitList(list: List<Item>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    inner class VH(private val b: ItemNastyazzBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: Item) {
            b.itemId.text = "ID: ${item.id}"
            b.itemTitle.text = item.title
            b.itemDescription.text = item.description
            b.itemImage.load(item.imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemNastyazzBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size
}
