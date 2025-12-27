package me.adrigamer2950.zenith.command

import dev.kord.core.entity.interaction.UserCommandInteraction
import dev.kord.core.event.interaction.GuildUserCommandInteractionCreateEvent
import dev.kord.rest.builder.interaction.UserCommandCreateBuilder
import kotlinx.coroutines.runBlocking
import me.adrigamer2950.zenith.client.Client

abstract class UserCommand<C : Client>(
    override val name: String,
    val builder: UserCommandCreateBuilder.() -> Unit = {}
) : Command<UserCommandEvent, UserCommandInteraction, C> {

    override fun register(client: C) {
        client.commandHandler.logger.debug("Registering user command ${this::class.simpleName}")

        runBlocking {
            client.kord.createGlobalUserCommand(this@UserCommand.name, this@UserCommand.builder)
        }
    }
}

typealias UserCommandEvent = GuildUserCommandInteractionCreateEvent