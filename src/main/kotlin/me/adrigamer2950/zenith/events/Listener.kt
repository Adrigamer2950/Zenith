package me.adrigamer2950.zenith.events

import dev.kord.core.event.Event
import me.adrigamer2950.zenith.logger.Logger

abstract class Listener<T : Event> {

    protected val logger = Logger.getLogger(this::class)

    abstract suspend fun execute(event: T)
}