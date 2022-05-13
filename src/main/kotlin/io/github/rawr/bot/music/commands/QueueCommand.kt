package io.github.rawr.bot.music.commands

import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.event.message.MessageCreateEvent
import io.github.rawr.bot.command.Command
import io.github.rawr.bot.music.MusicHandler
import io.github.rawr.bot.music.MusicQueue
import io.github.rawr.bot.music.VideoEntry
import io.github.rawr.bot.util.NumberEmoji

object QueueCommand : Command()
{
    override val names = listOf(
        "queue",
        "q",
        "qmusic"
    )

    override suspend fun handleCommand(event: MessageCreateEvent)
    {
        val message = event.message
        val content = message.content.split(" ")

        val query = content.subList(1, content.size).joinToString(" ")
        val results = MusicHandler.retrieve(query)

        val reply = message.channel.createEmbed {
            title = "music queue"

            field {
                name = "songs found under query: \"${query}\""
                value = results.mapIndexed { index, it ->
                    "${NumberEmoji.getEmoji(index + 1)} [${it.snippet.title}](https://youtube.com/watch?v=${it.id})"
                }.joinToString("\n")
            }

            footer {
                text = "react to choose a number to play."
            }
        }

        MusicQueue.queueEmbedData[reply.id] = results.map {
            VideoEntry(it)
        }

        results.forEachIndexed { index, _ ->
            reply.addReaction(
                ReactionEmoji.Unicode(NumberEmoji.getEmojiUnicode(index + 1))
            )
        }
    }
}