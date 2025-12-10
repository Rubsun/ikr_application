package com.example.ikr_application.dimmension.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ikr_application.R

class NamesFragment : Fragment() {
    private val viewModel by viewModels<NamesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_dimmension_names, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val randomNameView = view.findViewById<TextView>(R.id.random_name)
        val namesListContainer = view.findViewById<ViewGroup>(R.id.names_list)

        // Отображаем случайное имя
        val randomName = viewModel.getRandomName()
        randomNameView.text = getString(
            R.string.text_random_name_pattern,
            randomName.displayName,
            randomName.initials
        )

        // Отображаем список имен
        val names = viewModel.getMultipleNames(5)
        names.forEach { nameModel ->
            val nameView = layoutInflater.inflate(
                R.layout.item_dimmension_name,
                namesListContainer,
                false
            ) as TextView
            nameView.text = getString(
                R.string.text_name_item_pattern,
                nameModel.displayName,
                nameModel.shortName
            )
            namesListContainer.addView(nameView)
        }
    }
}

