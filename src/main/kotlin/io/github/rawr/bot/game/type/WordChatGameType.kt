package io.github.rawr.bot.game.type

import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.entity.Message
import dev.kord.core.entity.channel.MessageChannel
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.rawr.bot.game.ChatGameType
import io.github.rawr.bot.game.GameData
import io.github.rawr.bot.util.ColorUtil.randomColor
import java.util.concurrent.TimeUnit

object ReactionChatGameType : ChatGameType<ReactionGameData>()
{
    override val name = "reaction game"
    override val timeout = TimeUnit.SECONDS.toMillis(15)
    override val winnerAmount = 3

    override var currentGame: ReactionGameData? = null

    private val dictionary = listOf(
        "cow",
        "ice",
        "mom",
        "duplex",
        "channel",
        "bisector",
        "alphabet",
        "growl",
        "chai"
    )

    override suspend fun handleStart(
        channel: MessageChannel
    )
    {
        val word = dictionary.random()
        val message = channel.createEmbed {
            color = randomColor()
            title = "chat reaction"
            description = "type: `$word`"

            footer {
                text = "max $winnerAmount winners"
            }
        }

        this.currentGame = ReactionGameData(
            word,
            message
        )
    }

    override suspend fun handleMessage(message: Message)
    {
        val game = this.currentGame

        if (game != null)
        {
            if (message.content == game.word)
            {
                game.winners[message.author!!] = System.currentTimeMillis()

                if (game.winners.size == 2)
                {
                    this.endGame()
                }
            }
        }
    }
}

class ReactionGameData(
    val word: String,
    message: Message,
) : GameData(message)
{
    override suspend fun createEmbed(): EmbedBuilder.() -> Unit
    {
        return {
            color = randomColor()
            title = "chat reaction"
            description = "type: `$word`"
        }
    }
}