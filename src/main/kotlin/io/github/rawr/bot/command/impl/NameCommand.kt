package io.github.rawr.bot.command.impl

import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.event.message.MessageCreateEvent
import io.github.rawr.bot.command.Command
import io.github.rawr.bot.util.ColorUtil.randomColor
import io.github.rawr.bot.util.mojang.Name
import io.github.rawr.bot.util.mojang.NameUtil
import java.util.concurrent.TimeUnit

object NameCommand : Command()
{
    override val names = listOf(
        "names",
        "minecraft",
        "ign",
        "mc"
    )

    override suspend fun handleCommand(event: MessageCreateEvent)
    {
        val message = event.message
        val contents = message.content.split(' ')

        if (contents.size <= 1)
        {
            message.channel.createMessage("Usage: ${contents[0]} <name>")
            return
        }

        val uniqueId = NameUtil.nameToUUID(contents[1])
        val history = NameUtil.getNameHistory(uniqueId).reversed()

        message.channel.createEmbed {
            title = history[0].name
            color = randomColor()

            val date: (Name) -> String = {
                if (it.date == 0L)
                {
                    "first name"
                } else
                {
                    "<t:${it.date / 1000}>"
                }
            }

            description = history.joinToString("\n") {
                "``${it.name}`` | ${date.invoke(it)}"
            }

            thumbnail {
                url = "https://crafatar.com/renders/body/${uniqueId}?overlay"
            }

            val now = System.currentTimeMillis()

            val changeDate = history[0].date
            val changeDelay = TimeUnit.DAYS.toMillis(30)

            val timeDifference = (changeDate + changeDelay) - now

            if (timeDifference >= 0)
            {
                description += "\n\nname change available in <t:${(changeDate + changeDelay) / 1000}:R>"
            }
        }
    }
}