package io.github.rawr.bot.command.impl.percentage.type

import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.behavior.edit
import dev.kord.core.entity.Message
import dev.kord.core.entity.User
import dev.kord.rest.builder.message.modify.embed
import io.github.rawr.bot.command.impl.percentage.PercentageCommand
import io.github.rawr.bot.util.ColorUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

object LoveCommand : PercentageCommand()
{
    override val emojiMap: (Int) -> String = {
        when
        {
            it >= 80 -> ":heart:"
            it >= 60 -> ":blue_heart:"
            it >= 30 -> ":black_heart:"
            else -> ":full_moon_with_face:"
        }
    }

    override val sizeMap: (Int) -> String = {
        when
        {
            it >= 80 -> "go marry"
            it >= 60 -> "good for each other"
            it >= 30 -> "cute"
            else -> "bad news..."
        }
    }

    override val amount = 2
    override val type = "love meter"

    override val names = listOf(
        "love"
    )

    override suspend fun calculateAmount(users: Flow<User>): Int
    {
        val list = users.toList()

        val user1 = list[0]
        val user2 = list[1]

        if ((user1.id.value.equals(930130357695696976) && user2.id.value.equals(918716111179100190))
            || (user1.id.value.equals(918716111179100190) && user2.id.equals(930130357695696976))
        )
        {
            return 100
        }

        return super.calculateAmount(users)
    }

    override suspend fun generateEmbed(users: Flow<User>, message: Message)
    {
        val percentage = calculateAmount(message.mentionedUsers)
        val userSplit = message.mentionedUsers.toList().joinToString(" x ") { it.mention }

        val msg = message.channel.createEmbed {
            color = ColorUtil.randomColor()

            description = """
                $userSplit
                comparing dick length...
            """.trimIndent()

            title = "measuring..."
        }

        delay(1250)

        msg.edit {
            embed {
                color = ColorUtil.randomColor()

                description = """
                $userSplit
                comparing age...
            """.trimIndent()

                title = "measuring..."
            }
        }

        delay(1250)

        msg.edit {
            embed {
                color = ColorUtil.randomColor()

                description = """
                $userSplit
                ${percentage}% $type ${emojiMap.invoke(percentage)}
            """.trimIndent()

                title = sizeMap.invoke(percentage)
            }
        }
    }
}