package me.devadri.zenith.example.commands.parent

import dev.kord.core.behavior.interaction.respondEphemeral
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import me.devadri.zenith.client.DefaultClient
import me.devadri.zenith.command.impl.ChatCommandEvent
import me.devadri.zenith.command.impl.SubCommand

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