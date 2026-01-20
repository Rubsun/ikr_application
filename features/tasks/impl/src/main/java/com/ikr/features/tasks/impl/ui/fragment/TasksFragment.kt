package com.ikr.features.tasks.impl.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.ikr.features.tasks.impl.ui.screen.TasksScreen
import com.ikr.features.tasks.impl.ui.viewmodel.TasksViewModel
import com.ikr.libs.imageloader.ImageLoader
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Fragment для отображения списка задач
 */
internal class TasksFragment : Fragment() {

    private val viewModel: TasksViewModel by viewModel()
    private val imageLoader: ImageLoader by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        TasksScreen(
                            viewModel = viewModel,
                            imageLoader = imageLoader
                        )
                    }
                }
            }
        }
    }
}

