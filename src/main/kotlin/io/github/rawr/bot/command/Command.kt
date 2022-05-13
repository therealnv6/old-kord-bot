package io.github.rawr.bot.command

import dev.kord.common.entity.Permission
import dev.kord.core.event.message.MessageCreateEvent

abstract class Command
{
    abstract val names: List<String>
    open val permission: Permission = Permission.SendMessages

    abstract suspend fun handleCommand(
        event: MessageCreateEvent
    )
}