package io.github.rawr.bot.game

import dev.kord.core.behavior.edit
import dev.kord.core.entity.Message
import dev.kord.core.entity.channel.MessageChannel
import dev.kord.rest.builder.message.modify.embed

abstract class ChatGameType<T : GameData>
{
    abstract val name: String
    abstract val timeout: Long
    abstract val winnerAmount: Int
    abstract var currentGame: T?

    abstract suspend fun handleStart(
        channel: MessageChannel
    )

    abstract suspend fun handleMessage(
        message: Message
    )

    suspend fun endGame()
    {
        val game = currentGame

        if (game == null)
        {
            println("No game to end...")
            return
        }

        game.message.edit {
            embed {
                game.createEmbed().invoke(this)

                field {
                    name = "winners"

                    value = if (game.winners.isEmpty())
                    {
                        // this is required since discord does not support embeds without the `value`
                        // variable set (or at least, kord doesn't support this, unsure if this is a kord thing)
                        "no one reacted in time :slight_frown:"
                    } else
                    {
                        game.winners.entries
                            .reversed()
                            .joinToString("\n") {
                                "${it.key.mention} | `${(it.value.toDouble() - game.startTime.toDouble()) / 1000.0}` seconds"
                            }
                    }
                }
            }
        }

        this.currentGame!!.winners.keys.forEachIndexed { index, user ->
            ChatGameHandler.rewardWinner(user, index + 1)
        }

        this.currentGame = null

        ChatGameHandler.lastGame = System.currentTimeMillis()
        ChatGameHandler.ongoingGame = null
    }
}