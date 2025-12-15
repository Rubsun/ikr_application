package com.example.ikr_application.dimmension.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ikr_application.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

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
        val searchInput = view.findViewById<TextInputEditText>(R.id.search_input)
        val firstNameInput = view.findViewById<TextInputEditText>(R.id.first_name_input)
        val lastNameInput = view.findViewById<TextInputEditText>(R.id.last_name_input)
        val addNameButton = view.findViewById<MaterialButton>(R.id.add_name_button)
        val refreshButton = view.findViewById<MaterialButton>(R.id.refresh_button)

        // Подписываемся на изменения State через Flow
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                // Обновляем случайное имя
                state.randomName?.let { name ->
                    randomNameView.text = getString(
                        R.string.text_random_name_pattern,
                        name.displayName,
                        name.initials
                    )
                }

                // Обновляем список имен (используем отфильтрованный список)
                namesListContainer.removeAllViews()
                state.filteredNamesList.forEach { nameModel ->
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

        // Обработка поиска
        searchInput?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.updateSearchQuery(s?.toString() ?: "")
            }
        })

        // Обработка добавления имени
        addNameButton?.setOnClickListener {
            val firstName = firstNameInput?.text?.toString()?.trim() ?: ""
            val lastName = lastNameInput?.text?.toString()?.trim() ?: ""
            
            if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
                viewModel.addName(firstName, lastName)
                firstNameInput?.text?.clear()
                lastNameInput?.text?.clear()
            }
        }

        // Обработка обновления случайного имени
        refreshButton?.setOnClickListener {
            viewModel.refreshRandomName()
        }
    }
}

