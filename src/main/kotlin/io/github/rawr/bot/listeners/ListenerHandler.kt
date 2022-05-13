package io.github.rawr.bot.listeners

import dev.kord.core.event.Event
import dev.kord.core.event.guild.MemberJoinEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.event.message.MessageUpdateEvent
import dev.kord.core.event.message.ReactionAddEvent
import io.github.rawr.bot.listeners.impl.AutoRoleListener
import io.github.rawr.bot.listeners.impl.CommandListener
import io.github.rawr.bot.listeners.impl.MentionsListener
import io.github.rawr.bot.music.listener.QueryReactionListener

object ListenerHandler
{
    val listeners = hashMapOf(
        MessageCreateEvent::class to listOf(
            MentionsListener,
            CommandListener
        ),
        MemberJoinEvent::class to listOf(
            AutoRoleListener
        ),
        ReactionAddEvent::class to listOf(
            QueryReactionListener
        )
    )

    suspend inline fun <reified T : Event> event(): suspend T.() -> Unit
    {
        return {
            listeners[T::class]!!.forEach {
                it.handleEvent(this)
            }
        }
    }
}