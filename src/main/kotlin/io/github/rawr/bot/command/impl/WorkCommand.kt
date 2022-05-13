package io.github.rawr.bot.command.impl

import dev.kord.core.behavior.channel.createEmbed
import dev.kord.core.event.message.MessageCreateEvent
import io.github.rawr.bot.command.Command
import io.github.rawr.bot.user.UserProfileHandler.getProfile
import io.github.rawr.bot.util.ColorUtil.randomColor
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object WorkCommand : Command()
{
    override val names = listOf(
        "work",
        "earn"
    )

    private val dateFormat = SimpleDateFormat("mm:ss")
    private val timeoutDuration = TimeUnit.MINUTES.toMillis(5)
    private val timeouts = hashMapOf<Long, Long>()

    private val workTypes = hashMapOf(
        "blundered, and got fired" to true,
        "offered great work, but as you return to home you notice your dog made a gigantic mess and broke your computer. time to buy a new computer" to true,
        "was at work but suddenly your mom calls, she's in the hospital with a terrible concussion. you leave work early without notifying your boss, and he's not happy with you." to true,
        "did some great work, the boss is happy and is thinking of promoting you" to false,
        "is useless and the only job you could find was to be a prostitute, at least it pays well." to false,
        "made an open source project and got noticed by google, they're willing to hire you" to false
    )

    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun handleCommand(event: MessageCreateEvent)
    {
        val message = event.message
        val author = message.author!!
        var proceed = true

        timeouts.computeIfPresent(author.id.value.toLong()) { _, start ->
            val difference = System.currentTimeMillis() - (start + timeoutDuration)

            if (difference < 0)
            {
                proceed = false

                GlobalScope.launch(Dispatchers.IO) {
                    message.channel.createEmbed {
                        color = randomColor()
                        title = "you're exhausted"
                        description =
                            "you must wait `${dateFormat.format(Date((timeoutDuration - difference) - timeoutDuration))}` before you can work again."
                    }
                }

                return@computeIfPresent start
            }

            return@computeIfPresent -1
        }

        if (proceed)
        {
            val workType = workTypes.entries.random()
            val random = Random(System.nanoTime())

            var amount = random.nextInt(270)

            if (workType.value)
            {
                amount = 0 - amount
            }

            val emoji = when
            {
                amount > 0 -> ":chart_with_upwards_trend: | +"
                amount < 0 -> ":chart_with_downwards_trend: | "
                else -> ":neutral_face:"
            }

            message.channel.createEmbed {
                title = "work"
                color = randomColor()
                description = listOf(
                    "${author.mention} ${workType.key}",
                    "$emoji$${amount}"
                ).joinToString("\n")
            }

            author.getProfile().balance += amount
            this.timeouts[author.id.value.toLong()] = System.currentTimeMillis()
        }
    }
}