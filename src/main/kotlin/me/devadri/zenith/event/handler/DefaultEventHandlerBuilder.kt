package me.devadri.zenith.event.handler

import me.devadri.zenith.client.Client
import me.devadri.zenith.logger.Logger

class DefaultEventHandlerBuilder {

    var client: Client? = null
    var logger: Logger = Logger.getLogger(DefaultEventHandler::class)
    var packageName: String? = null
}