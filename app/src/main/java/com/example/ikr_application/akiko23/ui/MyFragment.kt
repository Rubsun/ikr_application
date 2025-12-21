package com.example.ikr_application.akiko23.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ikr_application.R
import com.example.ikr_application.akiko23.domain.Akiko23TimePrecision
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.materialswitch.MaterialSwitch
import coil3.load
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.core.view.children

class Akiko23Fragment : Fragment() {
    private val viewModel by viewModels<Akiko23TimeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_akiko23_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val headerView = view.findViewById<TextView>(R.id.text)
        val elapsedView = view.findViewById<TextView>(R.id.elapsed)
        val buttons = view.findViewById<MaterialButtonToggleGroup>(R.id.buttons)
        val imageView = view.findViewById<ImageView>(R.id.akiko23_image)
        val catSwitch = view.findViewById<MaterialSwitch>(R.id.show_cat_switch)

        // Настроим кнопку открытия экрана с Canvas
        view.findViewById<View>(R.id.open_canvas_button).setOnClickListener {
            openCanvasScreen()
        }

        // Настроим управление выбором точности через ToggleGroup
        setupPrecisionButtons(buttons)

        // Триггер для отображения кота
        catSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.toggleCat(isChecked)
        }

        // Загрузка картинки при помощи внешней библиотеки Coil
        imageView.load("https://http.cat/images/200.jpg")

        // Подписываемся на Flow<State> из ViewModel
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state().collectLatest { state ->
                applyState(state, headerView, elapsedView, buttons, imageView, catSwitch)
            }
        }
    }

    private fun setupPrecisionButtons(buttons: MaterialButtonToggleGroup) {
        if (buttons.childCount == 0) {
            Akiko23TimePrecision.entries
                .forEach { precision ->
                    val button = LayoutInflater.from(buttons.context)
                        .inflate(R.layout.item_akiko23_precision, buttons, false) as MaterialButton
                    button.text = precision.typeName
                    button.tag = precision
                    button.id = View.generateViewId()
                    buttons.addView(button)
                }
        }

        buttons.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener

            val button = group.findViewById<MaterialButton>(checkedId)
            val precision = button.tag as? Akiko23TimePrecision ?: return@addOnButtonCheckedListener
            viewModel.selectPrecision(precision)
        }
    }

    private fun applyState(
        state: Akiko23TimeViewModel.State,
        headerView: TextView,
        elapsedView: TextView,
        buttons: MaterialButtonToggleGroup,
        imageView: ImageView,
        catSwitch: MaterialSwitch,
    ) {
        headerView.text = getString(R.string.akiko23_text_time_pattern, state.headerText)
        elapsedView.text = getString(
            R.string.akiko23_text_time_from_reboot_pattern,
            state.elapsedText,
        )

        if (catSwitch.isChecked != state.showCat) {
            catSwitch.isChecked = state.showCat
        }

        imageView.visibility = if (state.showCat) View.VISIBLE else View.GONE
        if (state.showCat && imageView.drawable == null) {
            imageView.load("https://http.cat/images/200.jpg")
        }

        // Подсветка активной точности
        val targetButton = buttons.children
            .filterIsInstance<MaterialButton>()
            .firstOrNull { it.tag == state.selectedPrecision }

        val targetId = targetButton?.id ?: View.NO_ID
        if (targetId != View.NO_ID && buttons.checkedButtonId != targetId) {
            buttons.check(targetId)
        }
    }

    private fun openCanvasScreen() {
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.anim.slide_in_left,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.slide_out_right
            )
            .replace(R.id.container, Akiko23CanvasFragment())
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }
}
