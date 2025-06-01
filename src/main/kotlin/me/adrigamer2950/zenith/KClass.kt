package me.adrigamer2950.zenith

import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

fun KClass<*>.getTypeClass(name: String? = null): KClass<*>? = (java.genericSuperclass as? ParameterizedType)
    ?.actualTypeArguments
    ?.firstOrNull {
        if (name != null)
            it.toString().contains(name)
        else true
    }
    ?.let { (it as? Class<*>)?.kotlin }

fun KClass<*>.hasNoArgConstructor(): Boolean {
    return java.constructors.any { it.parameters.isEmpty() }
}