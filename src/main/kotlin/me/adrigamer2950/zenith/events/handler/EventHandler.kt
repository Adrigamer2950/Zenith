package me.adrigamer2950.zenith.events.handler

import me.adrigamer2950.zenith.events.Listener
import kotlin.reflect.KClass

interface EventHandler {

    fun loadEvents();

    fun registerListener(clazz: KClass<out Listener<*>>)

    fun registerListener(listener: Listener<*>) {
        registerListener(listener::class)
    }
}