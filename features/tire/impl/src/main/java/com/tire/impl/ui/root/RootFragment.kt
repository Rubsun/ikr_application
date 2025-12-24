package com.tire.impl.ui.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tire.impl.R
import com.tire.impl.ui.cases.CasesFragment
import com.tire.impl.ui.collection.CollectionFragment

internal class RootFragment : Fragment() {

    private val casesTag = "tire_cases"
    private val collectionTag = "tire_collection"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.fragment_root, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bottomNav: BottomNavigationView = view.findViewById(R.id.tireBottomNav)
        if (childFragmentManager.findFragmentById(R.id.tireChildContainer) == null) {
            showCases()
        }
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_cases -> {
                    showCases()
                    true
                }
                R.id.menu_collection -> {
                    showCollection()
                    true
                }
                else -> false
            }
        }
    }

    private fun showCases() {
        val fragment = childFragmentManager.findFragmentByTag(casesTag)
            ?: CasesFragment()

        childFragmentManager.beginTransaction()
            .replace(R.id.tireChildContainer, fragment, casesTag)
            .commit()
    }

    private fun showCollection() {
        val fragment = childFragmentManager.findFragmentByTag(collectionTag)
            ?: CollectionFragment()

        childFragmentManager.beginTransaction()
            .replace(R.id.tireChildContainer, fragment, collectionTag)
            .commit()
    }
}
