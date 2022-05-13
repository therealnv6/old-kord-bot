package io.github.rawr.bot.command.impl

import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.entity.Message
import dev.kord.core.entity.User
import dev.kord.core.event.message.MessageCreateEvent
import io.github.rawr.bot.command.Command
import io.github.rawr.bot.util.ColorUtil.randomColor

object MentionsCommand : Command()
{

    private val mentions = hashMapOf<Long, MutableList<Message>>()

    fun addMention(
        user: User,
        message: Message
    )
    {
        this.mentions.putIfAbsent(
            user.id.value.toLong(), mutableListOf()
        )

        this.mentions[user.id.value.toLong()]!!.add(message)
    }

    override val names = listOf(
        "mentions",
        "pings"
    )

    override suspend fun handleCommand(
        event: MessageCreateEvent
    )
    {
        val message = event.message
        val author = message.author!!
        val id = author.id.value

        message.channel.createEmbed {
            color = randomColor()
            title = "mentions"

            description = if (!mentions.containsKey(id.toLong()))
            {
                "nothing, random"
            } else
            {
                mentions[id.toLong()]!!.joinToString("\n") {
                    "**${it.author!!.tag}** - ${it.content}"
                }
            }
        }
    }
}