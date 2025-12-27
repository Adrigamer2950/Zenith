package me.adrigamer2950.zenith.command.handler

import me.adrigamer2950.zenith.client.Client
import me.adrigamer2950.zenith.logger.Logger

class DefaultCommandHandlerBuilder {

    var client: Client? = null
    var logger: Logger = Logger.getLogger(DefaultCommandHandler::class)
    var packageName: String? = null
}