package io.github.rawr.bot.settings

import dev.kord.common.entity.Snowflake
import dev.kord.core.entity.Member

class Settings
{
    val roles = mutableListOf<Long>()

    suspend fun assignRoles(
        member: Member
    )
    {
        this.roles
            .forEach {
                member.addRole(
                    Snowflake(it), "Automatically assigned role"
                )
            }
    }
}