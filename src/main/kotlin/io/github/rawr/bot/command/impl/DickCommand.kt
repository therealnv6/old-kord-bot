package io.github.rawr.bot.command.impl

import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.event.message.MessageCreateEvent
import io.github.rawr.bot.command.Command
import io.github.rawr.bot.util.ColorUtil.randomColor
import kotlinx.coroutines.flow.firstOrNull
import kotlin.random.Random

object DickCommand : Command()
{
    override val names = listOf(
        "dick",
        "pp",
        "cock"
    )

    override suspend fun handleCommand(
        event: MessageCreateEvent
    )
    {
        val message = event.message
        val target = message.mentionedUsers.firstOrNull() ?: message.author!!

        val random = Random(System.nanoTime())
        val size = random.nextInt(18)

        val sizeType = when
        {
            size <= 5 -> "small"
            size <= 8 -> "\"ok\""
            size <= 12 -> "decent"
            else -> "nv6 size"
        }

        message.channel.createEmbed {
            color = randomColor()

            description = """
                ${target.mention}
                8${"=".repeat(size)}D | :straight_ruler:  $size inches
            """.trimIndent()

            title = "$sizeType cock"
        }
    }
}