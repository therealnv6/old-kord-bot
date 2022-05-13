package io.github.rawr.bot.command.impl

import dev.kord.common.Color
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.event.message.MessageCreateEvent
import io.github.rawr.bot.command.Command

object RedLikeRosesCommand : Command()
{
    override val names = listOf(
        "redlikeroses",
        "rlr",
        "iwasntdreamingwhentheytoldmeyouweregone"
    )

    override suspend fun handleCommand(event: MessageCreateEvent)
    {
        event.message.channel.createEmbed {
            title = "RED LIKE ROSES"
            url = "https://www.youtube.com/watch?v=euuxPokAeIA"
            color = Color(255, 0, 0)

            thumbnail {
                url = "https://img.youtube.com/vi/euuxPokAeIA/hqdefault.jpg"
            }
        }
    }
}