package com.kristevt.impl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kristevt.impl.R
import com.kristevt.impl.ui.adapters.SongAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class KristevtFragment : Fragment() {

    private val viewModel by viewModels<KristevtViewModel>()
    private lateinit var adapter: SongAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.content_kristevt_songs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = SongAdapter(
            onLyricsClick = viewModel::getLyrics,
            onAddSong = viewModel::addSong,
            onDeleteSong = viewModel::deleteSong
        )

        val recycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.kristevtRecycler)
        val lyrics = view.findViewById<TextView>(R.id.songLyrics)
        val sortBtn = view.findViewById<Button>(R.id.songSort)

        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = adapter

        var asc = true
        sortBtn.setOnClickListener {
            asc = !asc
            viewModel.loadSongs(asc)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest {
                adapter.submitList(it.songs)
                lyrics.text = it.lyrics

                it.error?.message?.let { msg ->
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.loadSongs(true)
    }
}
