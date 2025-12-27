package me.devadri.zenith.example.commands.user

import dev.kord.core.behavior.interaction.respondEphemeral
import dev.kord.core.entity.interaction.UserCommandInteraction
import me.devadri.zenith.client.DefaultClient
import me.devadri.zenith.command.impl.UserCommand
import me.devadri.zenith.command.impl.UserCommandEvent

class UserExampleCommand : UserCommand<DefaultClient>("user") {

    override suspend fun execute(
        interaction: UserCommandInteraction,
        event: UserCommandEvent
    ) {
        interaction.respondEphemeral {
            content = "Example user command"
        }
    }
}