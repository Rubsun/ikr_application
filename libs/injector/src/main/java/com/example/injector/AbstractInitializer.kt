package com.example.injector

import androidx.startup.Initializer

abstract class AbstractInitializer<TYPE> : Initializer<TYPE> {
    // От кого зависит этот initializer - в данном случае он того, кто стартует KOIN
    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(InjectorInitializer::class.java)
    }
}