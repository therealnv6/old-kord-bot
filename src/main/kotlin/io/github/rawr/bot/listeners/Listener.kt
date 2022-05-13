package io.github.rawr.bot.listeners

import dev.kord.core.event.Event

abstract class Listener<T : Event>
{
    abstract suspend fun handleEvent(): suspend T.() -> Unit

    suspend fun handleEvent(value: Any)
    {
        handleEvent().invoke(value as T)
    }
}