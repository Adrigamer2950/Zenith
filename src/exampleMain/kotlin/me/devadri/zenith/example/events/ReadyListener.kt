package me.devadri.zenith.example.events

import dev.kord.core.event.gateway.ReadyEvent
import me.devadri.zenith.client.Client
import me.devadri.zenith.event.Listener

class ReadyListener : Listener<ReadyEvent, Client>() {

    override suspend fun execute(event: ReadyEvent, client: Client) {
        logger.info("Ready as ${event.self.username}")
    }
}