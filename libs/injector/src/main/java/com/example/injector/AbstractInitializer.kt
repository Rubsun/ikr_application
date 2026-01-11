package com.example.injector

import androidx.startup.Initializer
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

private typealias Definition<T> = Scope.(ParametersHolder) -> T

abstract class AbstractInitializer<TYPE> : Initializer<TYPE> {
    // От кого зависит этот initializer - в данном случае от того, кто стартует KOIN
    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(InjectorInitializer::class.java)
    }

    protected inline fun <reified T> Module.intoSetFactory(name: String, noinline definition: Definition<T>): KoinDefinition<T> {
        val qualifier = named(name)
        Injector.register(T::class, qualifier)
        return factory(qualifier, definition)
    }
}