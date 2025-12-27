package me.devadri.zenith.logger

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

class Logger internal constructor(val name: String) {

    companion object {
        private val ansiSerializer = ANSIComponentSerializer.ansi()
        private val miniMessageSerializer = MiniMessage.miniMessage()

        private const val CHARS_BETWEEN_LEVEL_AND_NAME = 5
        private const val CHARS_BETWEEN_NAME_AND_LOG = 20

        val isDebugEnabled = run {
            if (System.getProperty("zenith.debug")?.toBoolean() == true) return@run true

            val env = runCatching { System.getenv("ZENITH_DEBUG") }.getOrNull() ?: return@run false

            return@run env.toBoolean() || env == "1"
        }

        private val loggers: MutableMap<KClass<*>, Logger> = mutableMapOf()

        @JvmStatic
        fun getLogger(klass: KClass<*>): Logger {
            return loggers[klass] ?: run {
                val logger = Logger(klass.simpleName ?: "Unknown")

                loggers[klass] = logger

                logger
            }
        }
    }

    fun log(message: Component, level: LoggerLevel) {
        internalLog(message, level)
    }

    fun log(message: String, level: LoggerLevel) {
        internalLog(message, level)
    }

    internal fun internalLog(message: String, level: LoggerLevel, includeInfo: Boolean = true) {
        internalLog(miniMessageSerializer.deserialize(message), level, includeInfo)
    }

    internal fun internalLog(message: Component, level: LoggerLevel, includeInfo: Boolean = true) {
        val logToConsole = if (includeInfo) {
            val time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
            val spaces1 = " ".repeat((CHARS_BETWEEN_LEVEL_AND_NAME - level.name.length).coerceAtLeast(0))
            val spaces2 = " ".repeat((CHARS_BETWEEN_NAME_AND_LOG - name.length).coerceAtLeast(0))

            miniMessageSerializer.deserialize("<white><aqua>$time</aqua> | ${level.colored}$spaces1 | $name$spaces2 | </white><reset>")
                .append(message)
        } else {
            message
        }

        println(ansiSerializer.serialize(logToConsole))
    }

    fun info(message: String) {
        log(message, LoggerLevel.INFO)
    }

    fun warn(message: String, throwable: Throwable? = null) {
        log("<gold>$message", LoggerLevel.WARN)

        throwable?.toString()?.let { internalLog("<gold>$it</gold>", LoggerLevel.WARN, false) }
        throwable?.stackTrace?.forEach { internalLog("<gold>$it</gold>", LoggerLevel.WARN, false) }
    }

    fun error(message: String = "An error has occurred", throwable: Throwable? = null) {
        log("<red>$message", LoggerLevel.ERROR)

        throwable?.toString()?.let { internalLog("<red>$it</red>", LoggerLevel.WARN, false) }
        throwable?.stackTrace?.forEach { internalLog("<red>$it</red>", LoggerLevel.WARN, false) }
    }

    fun debug(message: String) {
        if (!isDebugEnabled) return

        log(message, LoggerLevel.DEBUG)
    }
}

enum class LoggerLevel(val colored: String) {
    INFO("<aqua>INFO</aqua>"),
    WARN("<yellow>WARN</yellow>"),
    ERROR("<red>ERROR</red>"),
    DEBUG("<green>DEBUG</green>"),
}
