package me.adrigamer2950.zenith.client

import dev.kord.core.Kord
import dev.kord.gateway.builder.LoginBuilder
import kotlinx.coroutines.runBlocking
import me.adrigamer2950.zenith.events.handler.DefaultEventHandler
import me.adrigamer2950.zenith.events.handler.EventHandler
import me.adrigamer2950.zenith.logger.Logger

/**
 * Defines a client
 */
open class Client(builder: ClientBuilder.() -> Unit) {

    protected val logger = Logger.getLogger()

    private val clientBuilder: ClientBuilder = ClientBuilder().apply(builder)
    private val token: String = clientBuilder.token ?: throw IllegalArgumentException("Token cannot be null")

    val kord: Kord = createKordInstance()

    val eventHandler: EventHandler = DefaultEventHandler {
        client = this@Client
        packageName = this@Client.clientBuilder.eventsPackage
    }

    private fun createKordInstance(): Kord {
        return runBlocking {
            logger.debug("Creating Kord instance...")
            return@runBlocking Kord(token) {
                enableShutdownHook = clientBuilder.enableShutdownHook
            }
        }
    }

    /**
     * Starts the bot
     */
    suspend fun start(builder: LoginBuilder.() -> Unit = {}) {
        logger.debug("Logging in to Discord...")

        kord.login(builder)
    }

    suspend fun shutdown() {
        logger.debug("Shutting down...")
        kord.logout()
    }
}