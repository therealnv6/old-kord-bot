package io.github.rawr.bot.command.impl

import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.event.message.MessageCreateEvent
import io.github.rawr.bot.command.Command
import io.github.rawr.bot.user.UserProfileHandler.getProfile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

object BalanceCommand : Command()
{
    override val names = listOf(
        "economy",
        "bal",
        "balance",
        "eco",
        "econ",
        "dollars",
        "money"
    )

    override suspend fun handleCommand(event: MessageCreateEvent)
    {
        val message = event.message

        val target = if (message.mentionedUsers.firstOrNull() != null)
        {
            message.mentionedUsers.first()
        } else
        {
            message.author!!
        }

        val data = target.getProfile()

        message.channel.createEmbed {
            title = "balance"
            description = ":dollar: ${data.balance}"
        }
    }
}