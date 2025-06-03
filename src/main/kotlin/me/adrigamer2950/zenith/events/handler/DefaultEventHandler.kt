package me.adrigamer2950.zenith.events.handler

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import dev.kord.core.on
import me.adrigamer2950.zenith.ReflectionsUtil
import me.adrigamer2950.zenith.client.Client
import me.adrigamer2950.zenith.events.Listener
import me.adrigamer2950.zenith.getTypeClass
import me.adrigamer2950.zenith.hasNoArgConstructor
import me.adrigamer2950.zenith.runAndReturnError
import org.jetbrains.annotations.ApiStatus.Internal
import java.lang.reflect.Modifier
import kotlin.reflect.KClass

class DefaultEventHandler(builder: DefaultEventHandlerBuilder.() -> Unit) : EventHandler {

    val builder = DefaultEventHandlerBuilder().apply(builder)

    private val client: Client = this.builder.client ?: throw IllegalArgumentException("Client cannot be null")
    private val logger = this.builder.logger
    private val packageToSearch: String = this.builder.packageName ?: throw IllegalArgumentException("Package cannot be null")

    private val listeners: Multimap<KClass<out Listener<*, *>>, Listener<*, *>> = HashMultimap.create()

    init {
        logger.debug("Registering internal listener...")
        registerInternalListener()

        loadEvents()
    }

    override fun loadEvents() {
        logger.info("Loading events...")

        ReflectionsUtil.searchForAllClasses(this.packageToSearch, Listener::class)
            .forEach {
                if (Modifier.isAbstract(it.java.modifiers)) return@forEach

                val result = runAndReturnError { registerListener(it) }

                result.error?.let { throwable ->
                    logger.error("An error has occurred while registering ${it.simpleName}", throwable)
                }
            }
    }

    override fun registerListener(clazz: KClass<out Listener<*, *>>) {
        if (!clazz.hasNoArgConstructor()) {
            logger.warn("&c${clazz.simpleName} cannot be auto-registered because it doesn't have a no-arg constructor")
            return
        }

        logger.debug("Registering listener ${clazz.simpleName}")

        listeners.put(clazz, clazz.java.getDeclaredConstructor().newInstance())
    }

    @Suppress("UNCHECKED_CAST")
    @Internal
    private fun registerInternalListener() {
        client.kord.on<dev.kord.core.event.Event> {
            listeners.entries()
                .filter { it.key.getTypeClass() == this::class }
                .mapNotNull { it.value as? Listener<dev.kord.core.event.Event, Client> }
                .forEach { it.execute(this, client) }
        }
    }
}
