package io.github.rawr.bot.listeners.impl

import dev.kord.core.event.guild.MemberJoinEvent
import io.github.rawr.bot.listeners.Listener
import io.github.rawr.bot.settings.SettingsHandler

object AutoRoleListener : Listener<MemberJoinEvent>()
{
    override suspend fun handleEvent(): suspend MemberJoinEvent.() -> Unit
    {
        return {
            SettingsHandler.settings!!.assignRoles(
                member
            )
        }
    }
}