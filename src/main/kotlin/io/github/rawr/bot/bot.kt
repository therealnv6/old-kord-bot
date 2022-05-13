package io.github.rawr.bot

import dev.kord.common.entity.PresenceStatus
import dev.kord.core.Kord
import dev.kord.core.event.Event
import dev.kord.core.event.gateway.ReadyEvent
import dev.kord.core.event.guild.MemberJoinEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.Intents
import dev.kord.gateway.PrivilegedIntent
import io.github.nosequel.data.DataHandler
import io.github.nosequel.data.connection.mongo.NoAuthMongoConnectionPool
import io.github.rawr.bot.game.ChatGameHandler
import io.github.rawr.bot.listeners.ListenerHandler
import io.github.rawr.bot.settings.SettingsHandler
import io.github.rawr.bot.shutdown.ShutdownHandler.shutdownHook
import io.github.rawr.bot.user.UserProfileHandler
import kotlinx.coroutines.flow.first

lateinit var client: Kord

@OptIn(PrivilegedIntent::class)
suspend fun main()
{
    client = Kord("OTI2NTI4MzgxMjI5NjA5MDAw.Yc8-zA.jBlohtP-_5Jl2zhvdqjt_51dWgs")

    client.on<MessageCreateEvent>()
    client.on<MemberJoinEvent>()
    client.on<ReactionAddEvent>()

    client.on<ReadyEvent> {
        UserProfileHandler.loadData()
        SettingsHandler.loadSettings()

        // start chat games
        ChatGameHandler.startThread(
            client, client.guilds.first()
        )
    }

    DataHandler
        .withConnectionPool<NoAuthMongoConnectionPool> {
            this.databaseName = "tits"
            this.hostname = "127.0.0.1"
            this.port = 27017
        }

    shutdownHook {
        UserProfileHandler.saveData()
        SettingsHandler.saveSettings()
    }

    client.login {
        this.intents += Intents(Intent.GuildMembers)

        this.presence {
            this.status = PresenceStatus.DoNotDisturb
            this.streaming(
                "Red like Roses",
                "https://www.youtube.com/watch?v=euuxPokAeIA"
            )
        }

        this.name = "tipsy"
    }
}

inline fun <reified T : Event> Kord.on()
{
    this.on<T> {
        ListenerHandler.event<T>().invoke(this)
    }
}