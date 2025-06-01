package me.adrigamer2950.zenith.events.handler

import me.adrigamer2950.zenith.client.Client
import me.adrigamer2950.zenith.logger.Logger

class DefaultEventHandlerBuilder {

    var client: Client? = null
    var logger: Logger = Logger.getLogger(DefaultEventHandler::class)
    var packageName: String? = null
}