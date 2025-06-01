package me.adrigamer2950.zenith.client

class ClientBuilder {

    var token: String? = null
    var enableShutdownHook: Boolean = false

    var eventsPackage: String? = null

    fun token(token: String) {
        this.token = token
    }
}