package com.example.injector

import android.content.Context
import androidx.startup.Initializer
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.core.context.startKoin

/**
 * Инициалайзер для конфигурирования KOIN. И для его старта
 * Он зарегистрирован в манифесте
 */
internal class InjectorInitializer : Initializer<Koin> {
    override fun create(context: Context): Koin {
        // стартуем KOIN, что бы в него можно было зависимости добавить
        return startKoin {
            androidContext(context.applicationContext)
        }.koin
    }

    // От кого зависит этот initializer - в данном случае ни от кого, он базовый
    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}