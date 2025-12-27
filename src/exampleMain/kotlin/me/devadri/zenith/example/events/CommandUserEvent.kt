package me.devadri.zenith.example.events

import dev.kord.core.behavior.interaction.respondEphemeral
import me.devadri.zenith.client.Client
import me.devadri.zenith.command.Command
import me.devadri.zenith.command.UserCommand
import me.devadri.zenith.command.UserCommandEvent
import me.devadri.zenith.event.Listener

class CommandUserEvent : Listener<UserCommandEvent, Client>() {

    override suspend fun execute(
        event: UserCommandEvent,
        client: Client
    ) {
        try {
            val command: Command<*, *, *>? = client.commandHandler.commands.firstOrNull {
                it is UserCommand && it.name == event.interaction.invokedCommandName
            }

            if (command == null) {
                event.interaction.respondEphemeral {
                    content = "❌ | Command '${event.interaction.invokedCommandName}' not found. Refresh your client to update the list of available commands"
                }

                return
            }

            (command as UserCommand).execute(event.interaction, event)
        } catch (e: Exception) {
            event.interaction.respondEphemeral {
                content = "❌ | An error happened: ${e.message}"
            }

            logger.warn("An error happened while executing ${this::class.simpleName}", e)
        }
    }
}