package io.github.rawr.bot.shutdown

import kotlinx.coroutines.runBlocking

object ShutdownHandler
{
    fun shutdownHook(
        hook: () -> Unit
    )
    {
        Runtime.getRuntime().addShutdownHook(object : Thread()
        {
            override fun run() = runBlocking {
                hook.invoke()
            }
        })
    }
}