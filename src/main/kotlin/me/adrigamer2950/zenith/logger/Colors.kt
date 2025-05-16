package me.adrigamer2950.zenith.logger

import org.fusesource.jansi.Ansi

object Colors {

    private val colors = arrayOf(
        arrayOf("&0", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).boldOff().toString()),
        arrayOf("&1", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).boldOff().toString()),
        arrayOf("&2", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).boldOff().toString()),
        arrayOf("&3", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).boldOff().toString()),
        arrayOf("&4", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).boldOff().toString()),
        arrayOf("&5", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).boldOff().toString()),
        arrayOf("&6", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).boldOff().toString()),
        arrayOf("&7", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).boldOff().toString()),
        arrayOf("&8", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).boldOff().toString()),
        arrayOf("&9", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).bold().toString()),
        arrayOf("&a", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).bold().toString()),
        arrayOf("&b", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).bold().toString()),
        arrayOf("&c", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).bold().toString()),
        arrayOf("&d", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).bold().toString()),
        arrayOf("&e", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).bold().toString()),
        arrayOf("&f", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).bold().toString()),
        arrayOf("&k", Ansi.ansi().a(Ansi.Attribute.BLINK_SLOW).toString()),
        arrayOf("&l", Ansi.ansi().a(Ansi.Attribute.UNDERLINE_DOUBLE).toString()),
        arrayOf("&m", Ansi.ansi().a(Ansi.Attribute.STRIKETHROUGH_ON).toString()),
        arrayOf("&n", Ansi.ansi().a(Ansi.Attribute.UNDERLINE).toString()),
        arrayOf("&o", Ansi.ansi().a(Ansi.Attribute.ITALIC).toString()),
        arrayOf("&r", Ansi.ansi().a(Ansi.Attribute.RESET).toString()),
    )

    @JvmStatic
    fun translate(input: String): String {
        var message = "$input&r"

        colors.forEach {
            val code: String = it[0]
            val ansi: String = it[1]

            message = message.replace(code, ansi, true)
        }

        return message
    }
}