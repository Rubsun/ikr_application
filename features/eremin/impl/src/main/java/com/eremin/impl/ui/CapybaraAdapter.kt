package com.eremin.impl.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eremin.api.domain.models.Capybara
import com.eremin.impl.R
import com.imageloader.api.ImageLoader

internal class CapybaraAdapter(
    private var capybaras: List<Capybara>,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<CapybaraAdapter.CapybaraViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CapybaraViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_capybara, parent, false)
        return CapybaraViewHolder(view)
    }

    override fun onBindViewHolder(holder: CapybaraViewHolder, position: Int) {
        holder.bind(capybaras[position])
    }

    override fun getItemCount(): Int = capybaras.size

    fun updateCapybaras(newCapybaras: List<Capybara>) {
        capybaras = newCapybaras
        notifyDataSetChanged()
    }

    internal inner class CapybaraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.capybara_image)
        private val altTextView: TextView = itemView.findViewById(R.id.capybara_alt)

        fun bind(capybara: Capybara) {
            imageLoader.load(imageView, capybara.url)
            altTextView.text = capybara.alt
        }
    }
}
