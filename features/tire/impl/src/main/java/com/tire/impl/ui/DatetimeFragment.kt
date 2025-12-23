package com.tire.impl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil3.load
import com.google.android.material.button.MaterialButton
import com.tire.impl.R
import com.tire.api.domain.TimePrecisions
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class DatetimeFragment : Fragment() {

    private val viewModel: DatetimeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_tire_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dateTextView = view.findViewById<TextView>(R.id.text)
        val elapsedTextView = view.findViewById<TextView>(R.id.elapsed)
        val buttonsContainer = view.findViewById<LinearLayout>(R.id.buttons)
        val searchEditText = view.findViewById<EditText>(R.id.searchEditText)
        val deviceNameInput = view.findViewById<EditText>(R.id.deviceNameInput)
        val addDeviceButton = view.findViewById<View>(R.id.addDeviceButton)
        val devicesListView = view.findViewById<TextView>(R.id.devicesList)
        val imageView = view.findViewById<ImageView>(R.id.sampleImage)

        imageView?.load("https://github.com/Rubsun/ikr_application/raw/main/oooo_fernando_alooooonsoo.gif")

        searchEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.onFilterChanged(text?.toString().orEmpty())
        }

        addDeviceButton.setOnClickListener {
            val deviceName = deviceNameInput.text.toString()
            viewModel.onAddDevice(deviceName)
            deviceNameInput.text.clear()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    dateTextView.text = getString(R.string.tire_text_time_pattern, state.dateText)
                    elapsedTextView.text = getString(
                        R.string.tire_text_time_from_reboot_pattern,
                        state.elapsedText
                    )

                    updatePrecisionButtons(buttonsContainer, state.precisions, state.selectedPrecision)

                    devicesListView.text = if (state.devices.isEmpty()) {
                        "Нет устройств"
                    } else {
                        state.devices.joinToString("\n") { device ->
                            "${device.deviceName} (ID: ${device.id})"
                        }
                    }
                }
            }
        }
    }

    private fun updatePrecisionButtons(
        container: LinearLayout,
        precisions: List<TimePrecisions>,
        selected: TimePrecisions
    ) {
        if (container.isEmpty()) {
            precisions.forEach { precision ->
                val button = layoutInflater.inflate(
                    R.layout.item_tire_precision,
                    container,
                    false
                ) as MaterialButton

                button.apply {
                    text = precision.typeName
                    tag = precision
                    backgroundTintList = ContextCompat.getColorStateList(
                        requireContext(),
                        if (precision == selected) R.color.tire_color_primary
                        else R.color.tire_color_black
                    )
                    setOnClickListener {
                        viewModel.onPrecisionSelected(precision)
                    }
                }
                container.addView(button)
            }
        } else {
            for (i in 0 until container.childCount) {
                val button = container.getChildAt(i) as? MaterialButton
                val precision = button?.tag as? TimePrecisions
                button?.backgroundTintList = ContextCompat.getColorStateList(
                    requireContext(),
                    if (precision == selected) R.color.tire_color_primary
                    else R.color.tire_color_black
                )
            }
        }
    }
}
