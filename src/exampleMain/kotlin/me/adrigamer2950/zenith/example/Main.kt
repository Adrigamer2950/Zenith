package me.adrigamer2950.zenith.example

import me.adrigamer2950.zenith.client.DefaultClient

suspend fun main() {
    val client = DefaultClient {
        token(System.getenv("DISCORD_TOKEN"))
        autoRegister {
            eventsPackage = "me.adrigamer2950.zenith.example.events"
            commandsPackage = "me.adrigamer2950.zenith.example.commands"
        }
    }

    client.start()
}