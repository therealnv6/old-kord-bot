package io.github.rawr.bot.game.type

import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.entity.Message
import dev.kord.core.entity.channel.MessageChannel
import dev.kord.rest.builder.message.EmbedBuilder
import io.github.rawr.bot.game.ChatGameType
import io.github.rawr.bot.game.GameData
import io.github.rawr.bot.util.ColorUtil.randomColor
import java.util.concurrent.TimeUnit

object ScrambledWordGameType : ChatGameType<ScrambledWordGameData>()
{
    override val name = "scrambled word"
    override val timeout = TimeUnit.SECONDS.toMillis(20)
    override val winnerAmount = 1
    override var currentGame: ScrambledWordGameData? = null

    private val dictionary = listOf(
        "cow",
        "ice",
        "mom",
        "duplex",
        "channel",
        "bisector",
        "alphabet",
        "growl",
        "development",
        "homophobia",
        "transphobia",
        "integer",
        "floating",
        "window",
        "manager",
        "handler",
        "command",
        "comment",
        "counter",
        "global",
        "offensive",
        "consider"
    )

    override suspend fun handleStart(channel: MessageChannel)
    {
        val word = dictionary.random()
        val scrambledWord = word.toCharArray()

        scrambledWord.shuffle()

        val message = channel.createEmbed {
            color = randomColor()
            title = "scrambled word"
            description = "unscramble: `${scrambledWord.concatToString()}`"

            footer {
                text = "max $winnerAmount winners"
            }
        }

        this.currentGame = ScrambledWordGameData(
            word,
            scrambledWord.concatToString(),
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

                if (game.winners.size == winnerAmount)
                {
                    this.endGame()
                }
            }
        }
    }
}

class ScrambledWordGameData(
    val word: String,
    private val scrambled: String,
    message: Message,
) : GameData(message)
{
    override suspend fun createEmbed(): EmbedBuilder.() -> Unit
    {
        return {
            color = randomColor()
            title = "scrambled word"
            description = "unscramble: `$scrambled` | `$word`"
        }
    }
}