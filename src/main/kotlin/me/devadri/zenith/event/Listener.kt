package me.devadri.zenith.event

import dev.kord.core.event.Event
import me.devadri.zenith.client.Client
import me.devadri.zenith.logger.Logger

abstract class Listener<E : Event, C : Client> {

    protected val logger = Logger.getLogger(this::class)

    abstract suspend fun execute(event: E, client: C)
}