package me.devadri.zenith.example

import me.devadri.zenith.client.DefaultClient

suspend fun main() {
    val client = DefaultClient {
        token(System.getenv("DISCORD_TOKEN"))
        autoRegister {
            eventsPackage = "me.devadri.zenith.example.events"
            commandsPackage = "me.devadri.zenith.example.commands"
        }
    }

    client.start()
}