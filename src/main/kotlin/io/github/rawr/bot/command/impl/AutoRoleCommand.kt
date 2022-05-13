package io.github.rawr.bot.command.impl

import dev.kord.common.entity.Permission
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.event.message.MessageCreateEvent
import io.github.rawr.bot.command.Command
import io.github.rawr.bot.settings.SettingsHandler

object AutoRoleCommand : Command()
{
    override val permission: Permission = Permission.Administrator
    override val names = listOf(
        "autorole",
        "ar"
    )

    override suspend fun handleCommand(event: MessageCreateEvent)
    {
        val message = event.message
        val roleIds = message.mentionedRoleIds

        val roles = roleIds
            .map { message.getGuild().getRole(it) }

        SettingsHandler.settings!!.roles.addAll(
            roleIds
                .map { it.value.toLong() }
        )

        message.channel.createEmbed {
            title = "changed auto roles"
            description = "the current roles assigned on guild join are `${
                roles.toList().joinToString(", ") { it.name }
            }`"
        }
    }
}