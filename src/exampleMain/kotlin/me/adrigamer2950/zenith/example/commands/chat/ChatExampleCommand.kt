package me.adrigamer2950.zenith.example.commands.chat

import dev.kord.core.behavior.interaction.respondEphemeral
import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import me.adrigamer2950.zenith.client.DefaultClient
import me.adrigamer2950.zenith.command.ChatCommand
import me.adrigamer2950.zenith.command.ChatCommandEvent

class ChatExampleCommand : ChatCommand<DefaultClient>("chat", "Example chat command") {

    override suspend fun execute(
        interaction: ChatInputCommandInteraction,
        event: ChatCommandEvent
    ) {
        interaction.respondEphemeral {
            content = "Example chat command"
        }
    }
}