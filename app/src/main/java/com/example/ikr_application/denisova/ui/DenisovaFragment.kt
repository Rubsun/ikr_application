package com.example.ikr_application.denisova.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ikr_application.R

class DenisovaFragment : Fragment() {
    private val viewModel by viewModels<DenisovaViewModel>()
    private val adapter by lazy { StudentAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_denisova_students, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@DenisovaFragment.adapter
        }
        adapter.submitList(viewModel.students())

        val averageText = view.findViewById<TextView>(R.id.average)
        val avg = viewModel.averageGrade()
        averageText.text = getString(R.string.denisova_text_average_pattern, avg)
    }
}
