package com.tire.impl.ui.collection

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.injector.inject
import com.imageloader.api.ImageLoader
import com.tire.api.domain.models.Pokemon
import com.tire.impl.R

internal class CollectionAdapter : RecyclerView.Adapter<CollectionAdapter.PokemonViewHolder>() {
    private val imageLoader: ImageLoader by inject()
    private val items = mutableListOf<Pokemon>()

    fun submitList(list: List<Pokemon>) {
        Log.d("CollectionAdapter", "submitList size=${list.size}")
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_collection_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imagePokemon: ImageView = view.findViewById(R.id.imagePokemon)
        private val textName: TextView = view.findViewById(R.id.textName)
        private val textRarity: TextView = view.findViewById(R.id.textRarity)
        private val badgeCount: TextView = view.findViewById(R.id.badgeCount)

        fun bind(item: Pokemon) {
            textName.text = item.name
            textRarity.text = item.rarity.name
            imageLoader.load(imagePokemon, item.imageUrl)
            if (item.ownedCount > 1) {
                badgeCount.visibility = View.VISIBLE
                badgeCount.text = "x${item.ownedCount}"
            } else {
                badgeCount.visibility = View.GONE
            }
        }
    }
}
