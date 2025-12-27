package me.devadri.zenith.command

import dev.kord.core.entity.interaction.ApplicationCommandInteraction
import dev.kord.core.event.interaction.ApplicationCommandInteractionCreateEvent
import me.devadri.zenith.client.Client

/**
 * Important!! Implementations of this interface
 * need to have a parameterless constructor
 */
interface Command<Event : ApplicationCommandInteractionCreateEvent, Interaction : ApplicationCommandInteraction, C: Client> {

    val name: String

    suspend fun execute(interaction: Interaction, event: Event)

    fun register(client: C)
}