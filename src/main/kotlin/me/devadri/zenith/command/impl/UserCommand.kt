package me.devadri.zenith.command.impl

import dev.kord.core.entity.interaction.UserCommandInteraction
import dev.kord.core.event.interaction.GuildUserCommandInteractionCreateEvent
import dev.kord.rest.builder.interaction.UserCommandCreateBuilder
import kotlinx.coroutines.runBlocking
import me.devadri.zenith.client.Client
import me.devadri.zenith.command.Command

abstract class UserCommand<C : Client>(
    override val name: String,
    val builder: UserCommandCreateBuilder.() -> Unit = {}
) : Command<UserCommandEvent, UserCommandInteraction, C> {

    override fun register(client: C) {
        runBlocking {
            client.kord.createGlobalUserCommand(this@UserCommand.name, this@UserCommand.builder)
        }
    }
}

typealias UserCommandEvent = GuildUserCommandInteractionCreateEvent