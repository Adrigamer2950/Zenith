package me.devadri.zenith.client

import dev.kord.core.Kord
import dev.kord.gateway.builder.LoginBuilder
import me.devadri.zenith.command.handler.CommandHandler
import me.devadri.zenith.event.handler.EventHandler
import me.devadri.zenith.logger.Logger

/**
 * Defines a client from scratch
 */
interface Client {

    val logger: Logger

    val eventHandler: EventHandler
    val commandHandler: CommandHandler

    val kord: Kord

    suspend fun start(builder: LoginBuilder.() -> Unit = {})

    suspend fun shutdown()
}