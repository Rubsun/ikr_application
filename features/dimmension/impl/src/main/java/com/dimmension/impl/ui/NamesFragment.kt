package com.dimmension.impl.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil3.load
import com.dimmension.impl.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

internal class NamesFragment : Fragment() {
    private val viewModel by viewModels<NamesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    state.randomName?.let { name ->
                        randomNameView.text = getString(
                            R.string.text_random_name_pattern,
                            name.displayName,
                            name.initials,
                        )
                    }

                    namesListContainer.removeAllViews()
                    state.filteredNamesList.forEach { nameModel ->
                        val nameView = layoutInflater.inflate(
                            R.layout.item_dimmension_name,
                            namesListContainer,
                            false,
                        )

                        val nameText = nameView.findViewById<TextView>(R.id.name_text)
                        val avatarImage = nameView.findViewById<AppCompatImageView>(R.id.avatar_image)

                        nameText.text = getString(
                            R.string.text_name_item_pattern,
                            nameModel.displayName,
                            nameModel.shortName,
                        )

                        avatarImage.load(nameModel.avatarUrl)
                        namesListContainer.addView(nameView)
                    }

                    addNameButton?.isEnabled = !state.isLoading
                    refreshButton?.isEnabled = !state.isLoading
                }
            }
        }

        searchInput?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                viewModel.updateSearchQuery(s?.toString().orEmpty())
            }
        })

        addNameButton?.setOnClickListener {
            val firstName = firstNameInput?.text?.toString()?.trim().orEmpty()
            val lastName = lastNameInput?.text?.toString()?.trim().orEmpty()

            if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
                viewModel.addName(firstName, lastName)
                firstNameInput?.text?.clear()
                lastNameInput?.text?.clear()
            }
        }

        refreshButton?.setOnClickListener {
            viewModel.refreshRandomName()
        }
    }
}


