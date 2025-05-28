package me.adrigamer2950.zenith.logger

import me.adrigamer2950.zenith.util.StackUtil
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass

class Logger private constructor(val name: String, private val parent: Logger? = null, private val useColors: Boolean = true) {

    companion object {
        @JvmStatic
        val cachedLoggers = mutableListOf<Logger>()

        @JvmStatic
        fun getLogger(parent: Logger? = null, useColors: Boolean = true, cache: Boolean = true): Logger {
            return getLogger(StackUtil.getCallerClass(), parent, useColors, cache)
        }

        @JvmStatic
        fun getLogger(clazz: KClass<*>, parent: Logger? = null, useColors: Boolean = true, cache: Boolean = true): Logger {
            if (clazz.simpleName == null)
                throw IllegalArgumentException("${clazz.simpleName} cannot be null")

            val name = clazz.simpleName!!

            if (cache)
                cachedLoggers.firstOrNull { it.name == name }?.let {
                    return it
                }

            val logger = Logger(name, parent, useColors)

            if (cache)
                cachedLoggers.add(logger)

            return logger
        }
    }

    private fun log(message: String, level: LoggerLevel, name: String = "[${this.name}] ") {
        val time = LocalTime.now()

        println(
            "[${time.format(DateTimeFormatter.ofPattern("HH:mm:ss"))} $level] ${if (parent != null) "[${parent.name}] " else ""}$name${
                if (useColors) Colors.translate(message) else message
            }"
        )
    }

    fun info(message: String) {
        log(message, LoggerLevel.INFO)
    }

    fun warn(message: String = "Something has occurred", throwable: Throwable? = null) {
        log("&6$message", LoggerLevel.WARN)

        throwable?.toString()?.let { log("&6$it", LoggerLevel.WARN, "") }
        throwable?.stackTrace?.forEach { log("&6$it", LoggerLevel.WARN, "") }
    }

    fun error(message: String = "An error has occurred", throwable: Throwable? = null) {
        log("&c$message", LoggerLevel.ERROR)

        throwable?.toString()?.let { log("&c$it", LoggerLevel.WARN, "") }
        throwable?.stackTrace?.forEach { log("&c$it", LoggerLevel.WARN, "") }
    }

    fun debug(message: String) {
        log(message, LoggerLevel.DEBUG)
    }
}

enum class LoggerLevel {
    INFO,
    WARN,
    ERROR,
    DEBUG
}
