package io.github.rawr.bot.game

import dev.kord.common.entity.Snowflake
import dev.kord.core.Kord
import dev.kord.core.entity.Guild
import dev.kord.core.entity.User
import dev.kord.core.entity.channel.MessageChannel
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.on
import io.github.rawr.bot.game.type.ReactionChatGameType
import io.github.rawr.bot.game.type.ScrambledWordGameType
import io.github.rawr.bot.user.UserProfileHandler.getProfile
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

object ChatGameHandler
{
    var ongoingGame: ChatGameType<*>? = null
    var lastGame = 0L

    private val gameTimeout = TimeUnit.MINUTES.toMillis(10)
    private const val rewardAmount = 350

    private const val gameChannel = 926832640487723048L
    private val gameTypes = listOf(
        ReactionChatGameType,
        ScrambledWordGameType
    )

    fun startThread(
        client: Kord,
        guild: Guild
    )
    {
        thread(start = true) {
            while (true)
            {
                runBlocking {
                    val game = ongoingGame
                    val channel = guild.getChannel(Snowflake(gameChannel))

                    if ((lastGame + gameTimeout < System.currentTimeMillis()) && game == null && channel is MessageChannel)
                    {
                        val gameType = gameTypes.random()

                        gameType.handleStart(channel)

                        client.on<MessageCreateEvent> {
                            if (message.channel == channel && gameType.currentGame != null)
                            {
                                gameType.handleMessage(message)
                            }
                        }

                        ongoingGame = gameType
                    } else if (game?.currentGame != null && channel is MessageChannel)
                    {
                        if (game.currentGame!!.startTime + game.timeout < System.currentTimeMillis())
                        {
                            game.endGame()
                        }
                    }
                }
            }
        }
    }

    fun rewardWinner(
        winner: User,
        place: Int
    )
    {
        val data = winner.getProfile()
        val amount = this.rewardAmount / place

        data.balance += amount
    }
}