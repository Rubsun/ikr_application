package com.tire.impl.ui.cases

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imageloader.api.ImageLoader
import com.example.injector.inject
import com.tire.api.domain.models.PokemonCase
import com.tire.impl.R

internal class CasesAdapter(
    private val onOpenClick: (PokemonCase) -> Unit,
    private val onPreviewClick: (PokemonCase) -> Unit,
) : RecyclerView.Adapter<CasesAdapter.CaseViewHolder>() {
    private val imageLoader: ImageLoader by inject()
    private val items = mutableListOf<PokemonCase>()

    fun submitList(list: List<PokemonCase>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_case, parent, false)
        return CaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CaseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class CaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imageCase: ImageView = view.findViewById(R.id.imageCase)
        private val textTitle: TextView = view.findViewById(R.id.textTitle)
        private val textDescription: TextView = view.findViewById(R.id.textDescription)
        private val buttonOpen: Button = view.findViewById(R.id.buttonOpen)

        fun bind(item: PokemonCase) {
            textTitle.text = item.name
            textDescription.text = item.description
            imageLoader.load(imageCase, item.imageUrl)

            buttonOpen.setOnClickListener {
                onOpenClick(item)
            }

            itemView.setOnClickListener {
                onPreviewClick(item)
            }
        }
    }
}
