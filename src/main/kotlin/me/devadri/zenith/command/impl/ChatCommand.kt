package me.devadri.zenith.command.impl

import dev.kord.core.entity.interaction.ChatInputCommandInteraction
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.rest.builder.interaction.GlobalChatInputCreateBuilder
import kotlinx.coroutines.runBlocking
import me.devadri.zenith.client.Client
import me.devadri.zenith.command.Command

abstract class ChatCommand<C : Client>(
    override val name: String,
    val description: String,
    val builder: GlobalChatInputCreateBuilder.() -> Unit = {}
) : Command<ChatCommandEvent, ChatInputCommandInteraction, C> {

    override fun register(client: C) {
        runBlocking {
            client.kord.createGlobalChatInputCommand(
                this@ChatCommand.name,
                this@ChatCommand.description,
                this@ChatCommand.builder
            )
        }
    }
}

typealias ChatCommandEvent = GuildChatInputCommandInteractionCreateEvent