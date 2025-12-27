package me.devadri.zenith.example.events

import dev.kord.core.behavior.interaction.respondEphemeral
import me.devadri.zenith.client.Client
import me.devadri.zenith.command.impl.ChatCommand
import me.devadri.zenith.command.impl.ChatCommandEvent
import me.devadri.zenith.command.Command
import me.devadri.zenith.command.impl.ParentCommand
import me.devadri.zenith.event.Listener
import me.devadri.zenith.util.CommandInteractionPair
import me.devadri.zenith.util.CommandUtil

class CommandGuildEvent : Listener<ChatCommandEvent, Client>() {

    override suspend fun execute(event: ChatCommandEvent, client: Client) {
        try {
            val command: Command<*, *, *>? = client.commandHandler.commands.firstOrNull {
                it.name == event.interaction.command.rootName
            }

            if (command == null) {
                event.interaction.respondEphemeral {
                    content = "❌ | Command '${event.interaction.command.rootName}' not found. Refresh your client to update the list of available commands"
                }

                return
            }

            if (command is ParentCommand && CommandUtil.isSubCommand(event.interaction.command)) {
                val subCommand = CommandUtil.getSubCommand(CommandInteractionPair(command, event.interaction.command))

                if (subCommand == null) {
                    event.interaction.respondEphemeral {
                        content = "❌ | Command '${event.interaction.command.data.options.value?.firstOrNull()!!.name}' not found. Refresh your client to update the list of available commands"
                    }

                    return
                }

                subCommand.execute(event.interaction, event)
            } else if (command is ParentCommand) {
                event.interaction.respondEphemeral {
                    content = "❌ | Command ${event.interaction.command.rootName} doesn't have any subcommands"
                }
            } else {
                (command as ChatCommand).execute(event.interaction, event)
            }
        } catch (e: Exception) {
            event.interaction.respondEphemeral {
                content = "❌ | An error happened: ${e.message}"
            }

            logger.warn("An error happened while executing ${this::class.simpleName}", e)
        }
    }
}