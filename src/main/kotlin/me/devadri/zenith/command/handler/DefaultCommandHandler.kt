package me.devadri.zenith.command.handler

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import me.devadri.zenith.client.Client
import me.devadri.zenith.command.Command
import me.devadri.zenith.runAndReturnError
import me.devadri.zenith.util.ReflectionsUtil
import java.lang.reflect.Modifier

class DefaultCommandHandler(builder: DefaultCommandHandlerBuilder.() -> Unit) : CommandHandler {

    val builder = DefaultCommandHandlerBuilder().apply(builder)

    private val client: Client = this.builder.client ?: throw IllegalArgumentException("Client cannot be null")
    override val logger = this.builder.logger
    private val packageToSearch: String =
        this.builder.packageName ?: throw IllegalArgumentException("Package cannot be null")

    override val commands: MutableList<Command<*, *, *>> = mutableListOf()

    override fun loadCommands() {
        val oldCommands = runBlocking { client.kord.getGlobalApplicationCommands().toList() }

        logger.info("Loading commands...")

        ReflectionsUtil.searchForAllClasses(this.packageToSearch, Command::class)
            .forEach {
                if (Modifier.isAbstract(it.java.modifiers)) return@forEach

                runAndReturnError { registerCommand(it) }.error?.let { throwable ->
                    logger.error("An error has occurred while registering ${it.simpleName}", throwable)
                }
            }

        logger.info("Removing old commands...")

        runBlocking {
            oldCommands.forEach { old ->
                commands.firstOrNull { cmd -> old.name == cmd.name }?.let {
                    return@forEach
                }

                logger.debug("Unregistering removed command: ${old.name} (${old.id})")
                old.delete()
            }
        }
    }

    override fun registerCommand(command: Command<*, *, *>) {
        @Suppress("UNCHECKED_CAST")
        val result = runAndReturnError { (command as Command<*, *, Client>).register(client) }

        result.error?.let { throwable ->
            if (throwable is NotImplementedError) {
                logger.error("Command ${command.name} failed to be registered", throwable)
            } else {
                logger.error("An error has occurred while registering ${command::class.java.simpleName}", throwable)
            }

            return
        }

        logger.debug("Command '${command.name}' registered successfully")

        commands.add(command)
    }
}