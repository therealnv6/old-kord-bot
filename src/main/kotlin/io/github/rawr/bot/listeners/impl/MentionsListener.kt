package io.github.rawr.bot.listeners.impl

import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.event.message.MessageCreateEvent
import io.github.rawr.bot.command.CommandHandler
import io.github.rawr.bot.command.impl.MentionsCommand
import io.github.rawr.bot.listeners.Listener
import kotlinx.coroutines.flow.collect

object MentionsListener : Listener<MessageCreateEvent>()
{
    override suspend fun handleEvent(): suspend MessageCreateEvent.() -> Unit
    {
        return {
            if (message.content == "<@!926528381229609000>")
            {
                message.channel.createEmbed {
                    title = "prefix"
                    description = "prefix in this guild is `${CommandHandler.prefix}`"
                }
            }

            message.mentionedUsers.collect {
                MentionsCommand.addMention(
                    it.asUser(),
                    message
                )
            }
        }
    }
}