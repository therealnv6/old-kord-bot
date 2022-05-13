package io.github.rawr.bot.command.impl

import dev.kord.common.entity.Permission
import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.event.message.MessageCreateEvent
import io.github.rawr.bot.command.Command
import kotlinx.coroutines.flow.firstOrNull

object AssignRolesCommand : Command()
{
    override val permission = Permission.ManageRoles
    override val names = listOf(
        "assignrole",
        "assign",
        "assignroles"
    )

    override suspend fun handleCommand(event: MessageCreateEvent)
    {
        val message = event.message

        val target = message.mentionedUsers.firstOrNull()
        val roleIds = message.mentionedRoleIds

        if (target == null)
        {
            message.channel.createEmbed {
                title = "wrong usage"
                description = "you must provide a target to give the roles to."
                footer {
                    text = message.author!!.tag
                }
            }

            return
        }

        val roles = roleIds
            .map { message.getGuild().getRole(it) }

        roleIds.forEach {
            val role = message.getGuild().getRoleOrNull(it)
            val member = target.asMemberOrNull(message.getGuild().id)

            if (member != null && role != null)
            {
                member.addRole(
                    it, "assigned role"
                )
            }
        }

        message.channel.createEmbed {
            title = "assigned roles"
            description = "assigned `${
                roles.joinToString { it.name }
            }` to `${target.tag}`"
        }
    }
}