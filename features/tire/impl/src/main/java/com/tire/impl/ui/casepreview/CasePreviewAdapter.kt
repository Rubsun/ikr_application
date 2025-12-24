package com.tire.impl.ui.casepreview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imageloader.api.ImageLoader
import com.example.injector.inject
import com.tire.api.domain.models.Pokemon
import com.tire.impl.R

internal class CasePreviewAdapter : RecyclerView.Adapter<CasePreviewAdapter.PokemonViewHolder>() {
    private val imageLoader: ImageLoader by inject()

    private val items = mutableListOf<Pokemon>()

    fun submitList(list: List<Pokemon>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_case_preview_pokemon, parent, false)
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

        fun bind(item: Pokemon) {
            textName.text = item.name
            textRarity.text = item.rarity.name
            imageLoader.load(imagePokemon, item.imageUrl)
        }
    }
}
