package io.github.rawr.bot.command.impl

import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.event.message.MessageCreateEvent
import io.github.nosequel.data.DataHandler
import io.github.nosequel.data.connection.mongo.MongoConnectionPool
import io.github.rawr.bot.client
import io.github.rawr.bot.command.Command
import io.github.rawr.bot.settings.SettingsHandler
import io.github.rawr.bot.util.ColorUtil.randomColor
import kotlin.time.ExperimentalTime

object DebugCommand : Command()
{
    override val names = listOf(
        "debug"
    )

    @OptIn(ExperimentalTime::class)
    override suspend fun handleCommand(event: MessageCreateEvent)
    {
        event.message.channel.createEmbed {
            title = "system debug"
            color = randomColor()

            field {
                val mongo = DataHandler.findConnection(MongoConnectionPool::class.java)

                name = "mongodb"
                value = if (mongo != null)
                {
                    "running since <t:R>"
                } else
                {
                    "not connected"
                }
                inline = true
            }

            field {
                name = "kotlin (kord)"
                value = "kotlin/jvm ${KotlinVersion.CURRENT}"
                inline = true
            }

            field {
                name = "average ping"
                value = "${client.gateway.averagePing!!.inWholeMilliseconds}ms"
            }

            footer {
                text = "running tits v${SettingsHandler.version} by rawr#0365"
                icon = "https://cdn.discordapp.com/avatars/918716111179100190/acd599943d887c4dda324bb0b3d9879e.webp?size=100"
            }
        }
    }
}