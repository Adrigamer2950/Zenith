package me.devadri.zenith.client

class ClientBuilder {

    var token: String? = null
    var enableShutdownHook: Boolean = false

    var autoRegisterBuilder: AutoRegisterBuilder? = null

    fun token(token: String) {
        this.token = token
    }

    fun autoRegister(builder: AutoRegisterBuilder.() -> Unit) {
        autoRegisterBuilder = if (autoRegisterBuilder != null) {
            autoRegisterBuilder!!.apply(builder)
        } else {
            AutoRegisterBuilder().apply(builder)
        }
    }
}

class AutoRegisterBuilder {
    var eventsPackage: String? = null
    var commandsPackage: String? = null
}