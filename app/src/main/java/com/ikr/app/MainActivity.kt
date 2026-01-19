package com.ikr.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.ikr.features.tasks.api.TasksFeatureProvider
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val tasksFeatureProvider: TasksFeatureProvider by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Добавляем Fragment
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(
                    android.R.id.content,
                    tasksFeatureProvider.getTasksFragment()
                )
            }
        }
    }
}

