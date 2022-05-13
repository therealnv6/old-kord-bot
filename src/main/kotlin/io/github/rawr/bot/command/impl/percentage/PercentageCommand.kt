package io.github.rawr.bot.command.impl.percentage

import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.entity.Message
import dev.kord.core.entity.User
import dev.kord.core.event.message.MessageCreateEvent
import io.github.rawr.bot.command.Command
import io.github.rawr.bot.util.ColorUtil.randomColor
import kotlinx.coroutines.flow.*
import kotlin.random.Random

abstract class PercentageCommand : Command()
{
    abstract val emojiMap: (Int) -> String
    abstract val sizeMap: (Int) -> String
    abstract val type: String

    open val amount: Int = 1

    open suspend fun calculateAmount(users: Flow<User>): Int
    {
        return Random(System.nanoTime()).nextInt(100)
    }

    open suspend fun generateEmbed(users: Flow<User>, message: Message)
    {
        val percentage = calculateAmount(message.mentionedUsers)

        message.channel.createEmbed {
            color = randomColor()

            description = """
                ${message.mentionedUsers.toList().joinToString(" x ") { it.mention }}
                ${percentage}% $type ${emojiMap.invoke(percentage)}
            """.trimIndent()

            title = sizeMap.invoke(percentage)
        }
    }

    override suspend fun handleCommand(event: MessageCreateEvent)
    {
        val message = event.message

        if (message.mentionedUsers.count() < amount)
        {
            message.channel.createMessage("Usage: ${message.content.split(' ')[0]} ${"<user>".repeat(amount)}")
            return
        }

        this.generateEmbed(message.mentionedUsers, message)
    }
}