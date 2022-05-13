package io.github.rawr.bot.command.impl

import dev.kord.common.entity.Permission
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.event.message.MessageCreateEvent
import io.github.rawr.bot.command.Command
import io.github.rawr.bot.util.ColorUtil.randomColor
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

object SpamCommand : Command()
{
    override val names = listOf(
        "spam"
    )

    override val permission = Permission.Administrator

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun handleCommand(event: MessageCreateEvent)
    {
        val message = event.message
        val content = message.content.split(' ')
        val target = message.mentionedUsers.firstOrNull()

        if (target == null)
        {
            message.channel.createEmbed {
                title = "invalid usage"
                description = "you must mention at least 1 user"
                color = randomColor()
            }
            return
        }

        val directMessages = target.getDmChannel()

        val spam = if (content.size == 2)
        {
            "tits"
        } else
        {
            content.toTypedArray()
                .copyOfRange(2, content.size)
                .joinToString(" ")
        }

        GlobalScope.launch {
            for (i in 0..50)
            {
                try
                {
                    directMessages.createEmbed {
                        title = "spammed"
                        description = spam
                        color = randomColor()
                    }
                } catch (ignored: Exception)
                {
                    message.channel.createEmbed {
                        title = "pussy"
                        description = "that user has private dms disabled"
                        color = randomColor()
                    }
                    return@launch
                }
            }
        }
    }
}