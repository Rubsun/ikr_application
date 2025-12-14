package com.example.ikr_application.stupishin.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.example.ikr_application.R
import com.example.ikr_application.databinding.ItemStupishinAnimeBinding
import com.example.ikr_application.stupishin.domain.models.Anime

class StuAnimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val b = ItemStupishinAnimeBinding.bind(view)

    fun bind(item: Anime) {
        b.title.text = item.title

        val ctx = itemView.context
        val parts = mutableListOf<String>()

        item.score?.let { score ->
            parts.add(ctx.getString(R.string.stu_anime_score_pattern, score))
        }

        item.year?.let { year ->
            parts.add(ctx.getString(R.string.stu_anime_year_pattern, year))
        }

        item.episodes?.let { episodes ->
            parts.add(ctx.getString(R.string.stu_anime_episodes_pattern, episodes))
        }

        b.meta.text = parts.joinToString(" • ")
        b.meta.visibility = if (parts.isEmpty()) View.GONE else View.VISIBLE

        b.poster.load(item.imageUrl)
    }
}
