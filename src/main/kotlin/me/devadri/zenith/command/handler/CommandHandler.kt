package me.devadri.zenith.command.handler

import me.devadri.zenith.command.Command
import me.devadri.zenith.hasNoArgConstructor
import me.devadri.zenith.logger.Logger
import java.lang.reflect.Modifier
import kotlin.reflect.KClass

interface CommandHandler {

    val logger: Logger

    val commands: MutableList<Command<*, *, *>>

    fun loadCommands()

    fun registerCommand(clazz: KClass<out Command<*, *, *>>) {
        if (Modifier.isAbstract(clazz.java.modifiers)) throw IllegalArgumentException("Command class cannot be abstract")

        if (!clazz.hasNoArgConstructor()) {
            throw IllegalArgumentException("Command class must have a no-arg constructor")
        }

        // TODO: Create handlers that can use parameterized constructors

        val command: Command<*, *, *> = clazz.java.getDeclaredConstructor().newInstance()

        registerCommand(command)
    }

    fun registerCommand(command: Command<*, *, *>)
}