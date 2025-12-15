package com.example.ikr_application.eremin.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.example.ikr_application.R
import com.example.ikr_application.eremin.domain.models.Capybara

class CapybaraAdapter(private val capybaras: MutableList<Capybara>) : RecyclerView.Adapter<CapybaraAdapter.CapybaraViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CapybaraViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_capybara, parent, false)
        return CapybaraViewHolder(view)
    }

    override fun onBindViewHolder(holder: CapybaraViewHolder, position: Int) {
        holder.bind(capybaras[position])
    }

    override fun getItemCount(): Int = capybaras.size

    @SuppressLint("NotifyDataSetChanged")
    fun setCapybaras(newCapybaras: List<Capybara>) {
        capybaras.clear()
        capybaras.addAll(newCapybaras)
        notifyDataSetChanged()
    }

    class CapybaraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.capybara_image)
        private val altTextView: TextView = itemView.findViewById(R.id.capybara_alt)

        fun bind(capybara: Capybara) {
            imageView.load(capybara.url)
            altTextView.text = capybara.alt
        }
    }
}
