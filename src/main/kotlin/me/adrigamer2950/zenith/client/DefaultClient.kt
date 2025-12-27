package me.adrigamer2950.zenith.client

import dev.kord.core.Kord
import kotlinx.coroutines.runBlocking
import me.adrigamer2950.zenith.command.handler.CommandHandler
import me.adrigamer2950.zenith.command.handler.DefaultCommandHandler
import me.adrigamer2950.zenith.event.handler.DefaultEventHandler
import me.adrigamer2950.zenith.event.handler.EventHandler

/**
 * Represents a client that provides a default implementation
 * so it can be used directly without needing to extend it.
 */
open class DefaultClient(builder: ClientBuilder.() -> Unit) : AbstractClient(builder) {

    override val kord = createKordInstance()

    override val eventHandler: EventHandler = DefaultEventHandler {
        client = this@DefaultClient
        packageName = clientBuilder.autoRegisterBuilder?.eventsPackage
    }
    override val commandHandler: CommandHandler = DefaultCommandHandler {
        client = this@DefaultClient
        packageName = clientBuilder.autoRegisterBuilder?.commandsPackage
    }

    init {
        commandHandler.loadCommands()
    }

    private fun createKordInstance(): Kord {
        return runBlocking {
            logger.debug("Creating Kord instance...")
            return@runBlocking Kord(token) {
                enableShutdownHook = clientBuilder.enableShutdownHook
            }
        }
    }
}