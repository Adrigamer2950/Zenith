package me.devadri.zenith.command

import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import dev.kord.rest.builder.interaction.SubCommandBuilder
import me.devadri.zenith.client.Client

abstract class SubCommand<C : Client>(
    override val name: String,
    val description: String,
    val builder: SubCommandBuilder.() -> Unit = {}
) : Command<ChatCommandEvent, ChatInputCommandInteraction, C> {

    override fun register(client: C) {
        // Do nothing
    }
}