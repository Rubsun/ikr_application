package com.ikr.features.tasks.impl

import com.ikr.features.tasks.impl.di.tasksModule
import org.koin.core.module.Module

/**
 * Инициализатор фича-модуля задач
 */
object TasksFeatureInitializer {
    /**
     * Получить Koin модуль для настройки DI
     */
    fun getModule(): Module = tasksModule
}

