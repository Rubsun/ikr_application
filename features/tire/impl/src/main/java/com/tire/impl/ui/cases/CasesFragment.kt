package com.tire.impl.ui.cases

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ProgressBar
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.injector.inject
import com.imageloader.api.ImageLoader
import com.tire.api.domain.models.CaseOpenResult
import com.tire.impl.R
import com.tire.impl.ui.allpokemons.AllPokemonsFragment
import com.tire.impl.ui.casepreview.CasePreviewFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.getValue
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.toColorInt

internal class CasesFragment : Fragment() {

    private val viewModel: CasesViewModel by viewModels()
    private val imageLoader: ImageLoader by inject()

    private lateinit var recyclerCases: RecyclerView
    private lateinit var progress: ProgressBar

    private val adapter = CasesAdapter(
        onOpenClick = { case -> viewModel.openCase(case.id) },
        onPreviewClick = { case -> openPreview(case.id) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.fragment_cases, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerCases = view.findViewById(R.id.recyclerCases)
        progress = view.findViewById(R.id.progress)
        val buttonAllPokemons: Button = view.findViewById(R.id.buttonAllPokemons)
        recyclerCases.adapter = adapter
        buttonAllPokemons.setOnClickListener {
            openAllPokemons()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                progress.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                adapter.submitList(state.cases)

                state.lastOpenResult?.let { result ->
                    showOpenResult(result)
                    viewModel.clearLastResult()
                }
            }
        }
    }

    private fun openPreview(caseId: String) {
        val fragment = CasePreviewFragment.newInstance(caseId)
        parentFragmentManager.beginTransaction()
            .replace(R.id.tireChildContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun openAllPokemons() {
        val fragment = AllPokemonsFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.tireChildContainer, fragment)
            .addToBackStack("all_pokemons")
            .commit()
    }

    private fun showOpenResult(result: CaseOpenResult) {
        val pokemon = result.pokemon
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_case_result)
        dialog.window?.apply {
            setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        val image = dialog.findViewById<ImageView>(R.id.imagePokemon)
        val name = dialog.findViewById<TextView>(R.id.textName)
        val badgeNew = dialog.findViewById<TextView>(R.id.badgeNew)
        val badgeRarity = dialog.findViewById<TextView>(R.id.badgeRarity)
        val buttonOk = dialog.findViewById<Button>(R.id.buttonOk)
        name.text = pokemon.name
        badgeNew.visibility = if (result.isNew) View.VISIBLE else View.GONE
        val rarity = pokemon.rarity.name.uppercase()
        val rarityColor = ContextCompat.getColor(requireContext(), getRarityColorRes(rarity))
        badgeRarity.apply {
            text = rarity
            visibility = View.VISIBLE
            background = GradientDrawable().apply {
                setColor(rarityColor)
                cornerRadius = 12f
            }
        }
        image.background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 16f
            setStroke(4, rarityColor)
            setColor("#F5F5F5".toColorInt())
        }
        image.clipToOutline = true
        imageLoader.load(view = image, url = pokemon.imageUrl)
        buttonOk.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }


    private fun getRarityColorRes(rarity: String): Int = when (rarity) {
        "COMMON" -> R.color.tire_pokemon_rarity_common
        "RARE" -> R.color.tire_pokemon_rarity_rare
        "EPIC" -> R.color.tire_pokemon_rarity_epic
        "LEGENDARY" -> R.color.tire_pokemon_rarity_legendary
        else -> R.color.tire_pokemon_rarity_common
    }

    private fun getGlowDrawable(color: Int): GradientDrawable {
        fun withAlpha(alpha: Int) = (alpha shl 24) or (color and 0x00FFFFFF)

        return GradientDrawable().apply {
            gradientType = GradientDrawable.RADIAL_GRADIENT
            cornerRadius = 12f
            colors = intArrayOf(
                withAlpha(0xCC), // центр – 80% opacity
                withAlpha(0x33), // середина
                withAlpha(0x11)  // край прозрачный
            )
            gradientRadius = 140f
        }
    }





}
