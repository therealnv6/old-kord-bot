package io.github.rawr.bot.music.listener

import dev.kord.common.annotation.KordVoice
import dev.kord.core.behavior.edit
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.rest.builder.message.modify.embed
import dev.kord.voice.AudioFrame
import io.github.rawr.bot.listeners.Listener
import io.github.rawr.bot.music.MusicQueue
import io.github.rawr.bot.util.NumberEmoji

object QueryReactionListener : Listener<ReactionAddEvent>()
{
    @OptIn(KordVoice::class)
    override suspend fun handleEvent(): suspend ReactionAddEvent.() -> Unit
    {
        return {
            if (!user.asUser().isBot)
            {
                val data = MusicQueue.queueEmbedData[message.id]

                if (data != null)
                {
                    val music = data[NumberEmoji.emojiToNumber(emoji) - 1]
                    val member = userAsMember

                    message.deleteAllReactions()

                    if (music.file == null)
                    {
                        message.edit {
                            embed {
                                title = "music queue"

                                field {
                                    name = "song ${emoji.name} has been selected"
                                    value = "downloading ${music.title}... please have patience"
                                }

                                footer {
                                    text = "this has been chosen by ${userAsMember!!.mention}"
                                }

                                thumbnail {
                                    url = music.thumbnail
                                }
                            }
                        }

                        music.download()
                    }

                    message.edit {
                        embed {
                            title = "music queue"

                            field {
                                name = "now playing..."
                                value = "[${music.title}](https://youtube.com/watch?v=${music.url})"
                            }

                            thumbnail {
                                url = music.thumbnail
                            }
                        }
                    }

                    member!!.getVoiceStateOrNull()!!.getChannelOrNull()?.connect {
                        audioProvider {
                            AudioFrame.SILENCE
                        }
                    }
                }
            }
        }
    }
}