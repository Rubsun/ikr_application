package com.ikr.features.tasks.api

import androidx.fragment.app.Fragment

/**
 * Провайдер для получения фрагмента задач
 */
interface TasksFeatureProvider {
    fun getTasksFragment(): Fragment
}

