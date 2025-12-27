package me.adrigamer2950.zenith.command

import dev.kord.rest.builder.interaction.GlobalChatInputCreateBuilder
import dev.kord.rest.builder.interaction.subCommand
import kotlinx.coroutines.runBlocking
import me.adrigamer2950.zenith.client.Client

abstract class ParentCommand<C : Client>(
    override val name: String,
    val description: String,
    builder: GlobalChatInputCreateBuilder.() -> Unit = { }
) : Command<Nothing, Nothing, C> {

    val subCommands: MutableList<SubCommand<C>> = mutableListOf()

    val builder: GlobalChatInputCreateBuilder.() -> Unit = {
        this.apply(builder)

        subCommands.forEach {
            subCommand(it.name, it.description, it.builder)
        }
    }

    fun addSubCommands(vararg commands: SubCommand<C>) {
        commands.forEach {
            subCommands.add(it)
        }
    }

    final override suspend fun execute(interaction: Nothing, event: Nothing) {
        throw IllegalAccessException("ParentCommand cannot be executed directly")
    }

    override fun register(client: C) {
        client.commandHandler.logger.debug("Registering parent command ${this::class.simpleName}")

        runBlocking {
            client.kord.createGlobalChatInputCommand(
                this@ParentCommand.name,
                this@ParentCommand.description,
                this@ParentCommand.builder
            )
        }
    }
}