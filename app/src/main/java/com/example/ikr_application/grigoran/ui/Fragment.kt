package com.example.ikr_application.grigoran.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ikr_application.R
import com.example.ikr_application.databinding.FragmentGrigoranBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class GrigoranFragment : Fragment(R.layout.fragment_grigoran) {

    // ViewBinding хранится в приватной переменной
    private var vb: FragmentGrigoranBinding? = null

    // Получаем ViewModel (автоматически создаётся)
    private val viewModel: ExampleViewModel by viewModels()

    // Наш RecyclerView адаптер:
    private lateinit var adapter: ExampleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("ExampleFragment", "onViewCreated called")

        // Привязка XML -> Kotlin
        val binding = FragmentGrigoranBinding.bind(view)
        vb = binding

        // Инициализация адаптера
        adapter = ExampleAdapter()
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter

        // Подписываемся на состояние UI из ViewModel
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->

                // Показываем/прячем прогресс
                binding.progress.visibility =
                    if (state.isLoading) View.VISIBLE else View.GONE

                // Показываем ошибку, если есть
                if (state.error != null) {
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = state.error
                } else {
                    binding.errorText.visibility = View.GONE
                }

                // Передаём список элементов в адаптер
                adapter.submitList(state.items)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vb = null      // предотвращаем утечки памяти
    }
}