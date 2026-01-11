package com.akiko23.impl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.akiko23.impl.R
import com.coil.api.ImageLoader
import com.example.injector.inject
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Фрагмент для экрана akiko23.
 */
internal class Akiko23Fragment : Fragment() {
    private val viewModel: Akiko23ViewModel by viewModel()
    private val imageLoader: ImageLoader by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_akiko23_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val quoteText = view.findViewById<TextView>(R.id.quote_text)
        val quoteCard = view.findViewById<com.google.android.material.card.MaterialCardView>(R.id.quote_card)
        val wolfImage = view.findViewById<ImageView>(R.id.wolf_image)
        val wolfImageCard = view.findViewById<com.google.android.material.card.MaterialCardView>(R.id.wolf_image_card)
        val loadButton = view.findViewById<MaterialButton>(R.id.load_quote_button)

        // Кнопка для загрузки волчьей цитаты
        loadButton.setOnClickListener {
            viewModel.loadRandomWolfQuote()
        }

        // Подписываемся на Flow<State> из ViewModel
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state().collectLatest { state ->
                applyState(state, quoteText, quoteCard, wolfImage, wolfImageCard)
            }
        }
    }

    private fun applyState(
        state: Akiko23ViewModel.State,
        quoteText: TextView,
        quoteCard: com.google.android.material.card.MaterialCardView,
        wolfImage: ImageView,
        wolfImageCard: com.google.android.material.card.MaterialCardView,
    ) {
        // Отображение цитаты
        if (state.quoteText != null) {
            quoteText.text = "\"${state.quoteText}\""
            quoteCard.visibility = View.VISIBLE
        } else {
            quoteCard.visibility = View.GONE
        }

        // Отображение картинки волка
        if (state.imageUrl != null) {
            imageLoader.load(wolfImage, state.imageUrl)
            wolfImageCard.visibility = View.VISIBLE
        } else {
            wolfImageCard.visibility = View.GONE
        }
    }
}

