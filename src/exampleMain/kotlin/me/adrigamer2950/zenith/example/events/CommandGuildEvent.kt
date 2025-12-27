package me.adrigamer2950.zenith.example.events

import dev.kord.core.behavior.interaction.respondEphemeral
import me.adrigamer2950.zenith.client.Client
import me.adrigamer2950.zenith.command.ChatCommand
import me.adrigamer2950.zenith.command.ChatCommandEvent
import me.adrigamer2950.zenith.command.Command
import me.adrigamer2950.zenith.command.ParentCommand
import me.adrigamer2950.zenith.event.Listener
import me.adrigamer2950.zenith.util.CommandInteractionPair
import me.adrigamer2950.zenith.util.CommandUtil

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