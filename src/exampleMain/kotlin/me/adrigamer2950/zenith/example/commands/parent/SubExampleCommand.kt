package me.adrigamer2950.zenith.example.commands.parent

import dev.kord.core.behavior.interaction.respondEphemeral
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import me.adrigamer2950.zenith.client.DefaultClient
import me.adrigamer2950.zenith.command.ChatCommandEvent
import me.adrigamer2950.zenith.command.SubCommand

class SubExampleCommand : SubCommand<DefaultClient>(
    "sub",
    "An example of a sub command"
) {

    override suspend fun execute(
        interaction: ChatInputCommandInteraction,
        event: ChatCommandEvent
    ) {
        interaction.respondEphemeral {
            content = "Example sub command"
        }
    }
}