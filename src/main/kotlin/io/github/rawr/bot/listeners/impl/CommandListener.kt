package io.github.rawr.bot.listeners.impl

import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.event.message.MessageCreateEvent
import io.github.rawr.bot.command.CommandHandler
import io.github.rawr.bot.listeners.Listener
import io.github.rawr.bot.util.ColorUtil

object CommandListener : Listener<MessageCreateEvent>()
{
    override suspend fun handleEvent(): suspend MessageCreateEvent.() -> Unit
    {
        return {
            val content = message.content
            val author = message.author
            var proceed = true

            if (content.toCharArray()[0] == CommandHandler.prefix)
            {
                if (CommandHandler.cooldowns.containsKey(author!!.id.value.toLong()))
                {
                    if (CommandHandler.cooldowns[author.id.value.toLong()]!! - System.currentTimeMillis() >= 0)
                    {
                        message.channel.createEmbed {
                            title = "slow the fuck down"
                            description = "you're still on a cooldown for `${
                                (CommandHandler.cooldowns[author.id.value.toLong()]!!.toDouble() - System.currentTimeMillis()
                                    .toDouble()) / 1000
                            }` seconds"
                            color = ColorUtil.randomColor()
                        }

                        proceed = false
                    }
                }

                if (proceed)
                {
                    val contents = content.split(' ')
                    val label = contents[0].substring(1 until contents[0].length)

                    val command = CommandHandler.commands.find { it.names.contains(label) }

                    if (command != null)
                    {
                        if (!message.getAuthorAsMember()!!.getPermissions().contains(command.permission))
                        {
                            message.channel.createMessage(
                                "You must have `${
                                    command.permission.javaClass.simpleName
                                }` permissions to perform that command."
                            )
                            
                            proceed = false
                        }

                        if (proceed)
                        {
                            command.handleCommand(this)
                        }
                    }

                    CommandHandler.cooldowns[author.id.value.toLong()] =
                        System.currentTimeMillis() + CommandHandler.cooldown
                }
            }
        }
    }
}