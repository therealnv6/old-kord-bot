package io.github.rawr.bot.command.impl.percentage.type

import io.github.rawr.bot.command.impl.percentage.PercentageCommand

// i'm not homophobic, just for funs.
object GayCommand : PercentageCommand()
{
    override val emojiMap: (Int) -> String = {
        when
        {
            it >= 80 -> ":rainbow_flag:"
            it >= 60 -> ":nail_care:"
            it >= 30 -> ":poop:"
            else -> ":full_moon_with_face:"
        }
    }

    override val sizeMap: (Int) -> String = {
        when
        {
            it >= 80 -> "aeris type beat"
            it >= 60 -> "bi sexual"
            it >= 30 -> "inferior to nv6"
            else -> "normal human being"
        }
    }

    override val type = "gay"
    override val names = listOf(
        "gay",
        "faggot",
        "faget",
        "fagget",
        "lesbian"
    )
}