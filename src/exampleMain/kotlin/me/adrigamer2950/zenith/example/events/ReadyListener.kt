package me.adrigamer2950.zenith.example.events

import dev.kord.core.event.gateway.ReadyEvent
import me.adrigamer2950.zenith.client.Client
import me.adrigamer2950.zenith.events.Listener

class ReadyListener : Listener<ReadyEvent, Client>() {

    override suspend fun execute(event: ReadyEvent, client: Client) {
        logger.info("Ready as ${event.self.username}")
    }
}