package me.devadri.zenith.util

import dev.kord.common.entity.optional.Optional
import dev.kord.common.entity.optional.OptionalBoolean
import dev.kord.core.entity.interaction.InteractionCommand
import me.devadri.zenith.command.Command
import me.devadri.zenith.command.ParentCommand
import me.devadri.zenith.command.SubCommand

object CommandUtil {

    fun isSubCommand(command: InteractionCommand): Boolean {
        if (command.data.options.value == null) return false

        if (command.data.options.value?.isEmpty() == true) return false

        val firstOpt = command.data.options.value?.firstOrNull()!!

        return (firstOpt.value is Optional.Missing
                && firstOpt.subCommands is Optional.Missing
                && firstOpt.focused is OptionalBoolean.Missing)
    }

    fun getSubCommand(pair: CommandInteractionPair<ParentCommand<*>>): SubCommand<*>? {
        if (!isSubCommand(pair.interaction)) throw IllegalArgumentException("Command is not a subcommand")

        val firstOpt = pair.interaction.data.options.value?.firstOrNull()!!

        val command: SubCommand<*>? = pair.command.subCommands.firstOrNull { it.name == firstOpt.name }

        return command
    }
}

class CommandInteractionPair<T : Command<*, *, *>>(val command: T, val interaction: InteractionCommand)