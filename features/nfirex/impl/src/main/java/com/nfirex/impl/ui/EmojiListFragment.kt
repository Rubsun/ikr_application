package com.nfirex.impl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nfirex.impl.R
import com.nfirex.impl.ui.adapters.EmojiAdapter
import com.nfirex.impl.ui.widgets.QueryListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class EmojiListFragment : Fragment() {
    private val emojiViewModel by viewModels<EmojiViewModel>()
    private val queryListener by lazy { QueryListener(emojiViewModel::search) }
    private val emojiAdapter = EmojiAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_nfirex_emoji_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<EditText>(R.id.search)
            .addTextChangedListener(queryListener)

        view.findViewById<TextView>(R.id.empty)
            .setText(R.string.caption_search_emoji_none)

        view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = emojiAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            emojiViewModel.state().collectLatest(::applyState)
        }
    }

    private fun applyState(state: EmojiViewModel.State) {
        view?.findViewById<EditText>(R.id.search)?.apply {
            if (text.toString() != state.query) {
                setText(state.query)
                setSelection(length())
            }
        }

        view?.findViewById<TextView>(R.id.empty)
            ?.isVisible = state.data.isEmpty() && !state.isPending

        view?.findViewById<View>(R.id.pending)?.isVisible = state.isPending

        if (!state.isPending) {
            emojiAdapter.submitList(state.data)
        }

        state.error?.message?.let { message ->
            Toast
                .makeText(requireContext(), message, Toast.LENGTH_LONG)
                .show()
        }
    }
}