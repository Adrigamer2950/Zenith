package me.adrigamer2950.zenith.events

import dev.kord.core.event.Event
import me.adrigamer2950.zenith.client.Client
import me.adrigamer2950.zenith.logger.Logger

abstract class Listener<E : Event, C : Client> {

    protected val logger = Logger.getLogger(this::class)

    abstract suspend fun execute(event: E, client: C)
}