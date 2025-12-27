package me.adrigamer2950.zenith.example.commands.user

import dev.kord.core.behavior.interaction.respondEphemeral
import dev.kord.core.entity.interaction.UserCommandInteraction
import me.adrigamer2950.zenith.client.DefaultClient
import me.adrigamer2950.zenith.command.UserCommand
import me.adrigamer2950.zenith.command.UserCommandEvent

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