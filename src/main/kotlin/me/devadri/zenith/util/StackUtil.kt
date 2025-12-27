package me.devadri.zenith.util

import kotlin.reflect.KClass

object StackUtil {

    @JvmStatic
    fun getCallerClass(): KClass<*> = Class.forName(Exception().stackTrace.map { it.className }[3]).kotlin
}