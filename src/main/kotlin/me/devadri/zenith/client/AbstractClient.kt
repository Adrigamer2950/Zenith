package me.devadri.zenith.client

import dev.kord.gateway.builder.LoginBuilder
import me.devadri.zenith.logger.Logger

/**
 * Represents an abstract client that provides
 * common functionality but still requires to
 * be extended for proper implementation.
 */
abstract class AbstractClient(builder: ClientBuilder.() -> Unit) : Client {

    override val logger: Logger = Logger.getLogger(this::class)

    protected val clientBuilder: ClientBuilder = ClientBuilder().apply(builder)
    protected val token: String = clientBuilder.token ?: throw IllegalArgumentException("Token cannot be null")

    /**
     * Starts the bot
     */
    override suspend fun start(builder: LoginBuilder.() -> Unit) {
        logger.debug("Logging in to Discord...")

        kord.login(builder)
    }

    override suspend fun shutdown() {
        logger.debug("Shutting down...")

        kord.logout()
    }
}