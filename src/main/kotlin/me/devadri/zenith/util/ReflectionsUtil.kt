package me.devadri.zenith.util

import org.reflections.Reflections
import org.reflections.util.ConfigurationBuilder
import kotlin.reflect.KClass
import kotlin.streams.toList

object ReflectionsUtil {

    @JvmStatic
    fun <T : Any> searchForAllClasses(packageName: String, subTypesOf: KClass<out T>): List<KClass<out T>> {
        val reflections = Reflections(
            ConfigurationBuilder().forPackages(packageName)
                .disableLogging()
        )

        return reflections.getSubTypesOf(subTypesOf.java)
            .stream().map { it.kotlin }.toList()
    }
}