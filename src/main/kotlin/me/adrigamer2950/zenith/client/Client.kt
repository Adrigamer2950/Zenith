package me.adrigamer2950.zenith.client

import dev.kord.core.Kord
import dev.kord.gateway.builder.LoginBuilder
import me.adrigamer2950.zenith.event.handler.EventHandler
import me.adrigamer2950.zenith.logger.Logger

/**
 * Defines a client from scratch
 */
interface Client {

    val logger: Logger

    val eventHandler: EventHandler

    val kord: Kord

    suspend fun start(builder: LoginBuilder.() -> Unit = {})

    suspend fun shutdown()
}