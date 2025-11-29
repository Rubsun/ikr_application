package com.example.ikr_application.navigation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ikr_application.R

private val EXTRAS_SCREEN = "EXTRAS.screen"

class ScreensFragment : Fragment(R.layout.content_navigation_screens), ScreensAdapter.Listener {
    private val screensAdapter = ScreensAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = screensAdapter
        }

        screensAdapter.submitList(Screens.entries)
    }

    override fun onClicked(item: Screens) {
        val deeplink = "ikrapp://screens".toUri()
        val intent = Intent(Intent.ACTION_VIEW, deeplink).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            putExtra(EXTRAS_SCREEN, item.name)
        }

        startActivity(intent)
    }

    companion object {
        fun Intent.extractScreen(): Screens? {
            val screen = getStringExtra(EXTRAS_SCREEN)
            if (screen != null) {
                return Screens.valueOf(screen)
            }

            return null
        }
    }
}