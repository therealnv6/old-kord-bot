package io.github.rawr.bot.command.impl.percentage.type

import io.github.rawr.bot.command.impl.percentage.PercentageCommand

object StupidCommand : PercentageCommand()
{
    override val emojiMap: (Int) -> String = {
        when
        {
            it >= 80 -> ":x::brain:"
            it >= 60 -> ":woman:"
            it >= 30 -> ":earth_americas:"
            else -> ":nerd:"
        }
    }

    override val sizeMap: (Int) -> String = {
        when
        {
            it >= 80 -> "pure retard"
            it >= 60 -> "woman"
            it >= 30 -> "american"
            else -> "einstein"
        }
    }

    override val type = "stupid"
    override val names = listOf(
        "stupid",
        "idiot",
    )
}