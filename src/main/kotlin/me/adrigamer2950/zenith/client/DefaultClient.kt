package me.adrigamer2950.zenith.client

import dev.kord.core.Kord
import kotlinx.coroutines.runBlocking
import me.adrigamer2950.zenith.events.handler.DefaultEventHandler
import me.adrigamer2950.zenith.events.handler.EventHandler

/**
 * Represents a client that provides a default implementation
 * so it can be used directly without needing to extend it.
 */
open class DefaultClient(builder: ClientBuilder.() -> Unit) : AbstractClient(builder) {

    override val eventHandler: EventHandler = DefaultEventHandler {
        packageName = clientBuilder.autoRegisterBuilder?.eventsPackage
    }

    override val kord = createKordInstance()

    private fun createKordInstance(): Kord {
        return runBlocking {
            logger.debug("Creating Kord instance...")
            return@runBlocking Kord(token) {
                enableShutdownHook = clientBuilder.enableShutdownHook
            }
        }
    }
}