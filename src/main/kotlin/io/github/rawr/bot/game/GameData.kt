package io.github.rawr.bot.game

import dev.kord.core.entity.Message
import dev.kord.core.entity.User
import dev.kord.rest.builder.message.EmbedBuilder

abstract class GameData(
    val message: Message
)
{
    val winners = hashMapOf<User, Long>()
    val startTime: Long = System.currentTimeMillis()

    abstract suspend fun createEmbed() : EmbedBuilder.() -> Unit
}