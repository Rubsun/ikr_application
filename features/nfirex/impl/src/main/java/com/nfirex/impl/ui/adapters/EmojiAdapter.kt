package com.nfirex.impl.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nfirex.impl.R
import com.nfirex.api.domain.models.Emoji

internal class EmojiAdapter : ListAdapter<Emoji, EmojiViewHolder>(EmojiCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmojiViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nfirex_emoji, parent, false)

        return EmojiViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: EmojiViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    private class EmojiCallback: DiffUtil.ItemCallback<Emoji>() {
        override fun areItemsTheSame(
            oldItem: Emoji,
            newItem: Emoji
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: Emoji,
            newItem: Emoji
        ): Boolean {
            return oldItem == newItem
        }
    }
}