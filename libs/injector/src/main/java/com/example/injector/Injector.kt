package com.example.injector

import org.koin.core.component.KoinComponent
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.StringQualifier
import org.koin.mp.KoinPlatformTools
import kotlin.reflect.KClass

object Injector : KoinComponent {
    private val intoSets: MutableMap<KClass<*>, MutableSet<StringQualifier>> = mutableMapOf()

    fun register(klass: KClass<*>, qualifier: StringQualifier) {
        val set = intoSets[klass]
            ?: mutableSetOf<StringQualifier>().apply { intoSets[klass] = this }
        set.add(qualifier)
    }

    fun qualifiers(klass: KClass<*>): Set<Qualifier> {
        return intoSets[klass] ?: emptySet()
    }

    inline fun <reified T : Any> get(
        qualifier: Qualifier? = null,
        noinline parameters: ParametersDefinition? = null,
    ): T {
        return getKoin().get(clazz = T::class, qualifier = qualifier, parameters = parameters)
    }
}

inline fun <reified T : Any> get(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
): T {
    return Injector.get<T>(qualifier, parameters)
}

inline fun <reified T : Any> inject(
    qualifier: Qualifier? = null,
    mode: LazyThreadSafetyMode = KoinPlatformTools.defaultLazyMode(),
    noinline parameters: ParametersDefinition? = null,
): Lazy<T> =
    lazy(mode) { Injector.get<T>(qualifier, parameters) }

inline fun <reified T : Any> all(): List<Pair<String, T>> {
    return Injector
        .qualifiers(T::class)
        .map { qualifier -> qualifier.value to Injector.get<T>(qualifier, null) }

}