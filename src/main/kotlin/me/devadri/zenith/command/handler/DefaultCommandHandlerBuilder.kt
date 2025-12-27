package me.devadri.zenith.command.handler

import me.devadri.zenith.client.Client
import me.devadri.zenith.logger.Logger

class DefaultCommandHandlerBuilder {

    var client: Client? = null
    var logger: Logger = Logger.getLogger(DefaultCommandHandler::class)
    var packageName: String? = null
}