package com.nfirex.impl.ui.adapters

import android.view.View
import android.widget.TextView
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.RecyclerView
import com.nfirex.api.domain.models.Emoji
import com.nfirex.impl.R

internal class EmojiViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    private val emoji by lazy { view.findViewById<TextView>(R.id.emoji) }
    private val name by lazy { view.findViewById<TextView>(R.id.name) }

    fun bind(item: Emoji) {
        name.text = item.name
        emoji.text = item.codes.joinToString().parseAsHtml()
    }
}