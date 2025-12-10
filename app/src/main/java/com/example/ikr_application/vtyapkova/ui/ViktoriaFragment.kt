package com.example.ikr_application.vtyapkova.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ikr_application.R

class ViktoriaFragment : Fragment() {
    private val viewModel by viewModels<ViktoriaViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_vtyapkova_names, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val randomViktoriaView = view.findViewById<TextView>(R.id.random_name)
        val ViktoriaListContainer = view.findViewById<ViewGroup>(R.id.names_list)

        // Отображаем случайное имя
        val randomViktoria = viewModel.getRandomViktoria()
        randomViktoriaView.text = getString(
            R.string.text_random_name_pattern,
            randomViktoria.displayViktoria,
            randomViktoria.initials
        )

        // Отображаем список имен
        val names = viewModel.getMultipleViktoria(5)
        names.forEach { ViktoriaModel ->
            val ViktoriaView = layoutInflater.inflate(
                R.layout.item_vtyapkova_name,
                ViktoriaListContainer,
                false
            ) as TextView
            ViktoriaView.text = getString(
                R.string.text_name_item_pattern,
                ViktoriaModel.displayViktoria,
                ViktoriaModel.shortViktoria
            )
            ViktoriaListContainer.addView(ViktoriaView)
        }
    }
}