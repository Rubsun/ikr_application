package com.tire.impl.ui.allpokemons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.injector.inject
import com.imageloader.api.ImageLoader
import com.tire.api.domain.models.Pokemon
import com.tire.impl.R

internal class AllPokemonsAdapter :
    PagingDataAdapter<Pokemon, AllPokemonsAdapter.PokemonViewHolder>(PokemonDiffCallback()) {

    private val imageLoader: ImageLoader by inject()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pokemon_all, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imagePokemon: ImageView = view.findViewById(R.id.imagePokemon)
        private val textName: TextView = view.findViewById(R.id.textName)
        private val textRarity: TextView = view.findViewById(R.id.textRarity)

        fun bind(pokemon: Pokemon?) {
            pokemon ?: return
            textName.text = "#${pokemon.id} ${pokemon.name}"
            textRarity.text = pokemon.rarity.name
            imageLoader.load(imagePokemon, pokemon.imageUrl)
        }
    }
}

private class PokemonDiffCallback : DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
        oldItem == newItem
}
