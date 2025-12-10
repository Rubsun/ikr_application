package com.example.ikr_application.rubsun.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ikr_application.R

class NumberFragment : Fragment() {
    private val viewModel by viewModels<NumberViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_rubsun_numbers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val randomNumberView = view.findViewById<TextView>(R.id.random_number)
        val numbersListContainer = view.findViewById<ViewGroup>(R.id.numbers_list)

        // Отображаем случайное число
        val randomNumber = viewModel.getRandomNumber()
        randomNumberView.text = getString(
            R.string.rubsun_text_random_number_pattern,
            randomNumber.value,
            randomNumber.label,
            randomNumber.squared
        )

        // Отображаем список чисел
        val numbers = viewModel.getAllNumbers()
        numbers.forEach { numberModel ->
            val numberView = layoutInflater.inflate(
                R.layout.item_rubsun_number,
                numbersListContainer,
                false
            ) as TextView
            numberView.text = getString(
                R.string.rubsun_text_number_item_pattern,
                numberModel.value,
                numberModel.label,
                numberModel.squared
            )
            numbersListContainer.addView(numberView)
        }
    }
}
