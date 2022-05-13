package io.github.rawr.bot.command.impl.percentage.type

import io.github.rawr.bot.command.impl.percentage.PercentageCommand

// i'm not transphobic, just for funs.
object TrannyCommand : PercentageCommand()
{
    override val emojiMap: (Int) -> String = {
        when
        {
            it >= 80 -> ":transgender_flag:"
            it >= 60 -> ":nail_care:"
            it >= 30 -> ":rainbow_flag:"
            else -> ":full_moon_with_face:"
        }
    }

    override val sizeMap: (Int) -> String = {
        when
        {
            it >= 80 -> "absolutely trans"
            it >= 60 -> "femboy/tomboy"
            it >= 30 -> "cross dresser"
            else -> "normal human being"
        }
    }

    override val type = "trans"
    override val names = listOf(
        "tranny",
        "trans",
        "transgender",
    )
}