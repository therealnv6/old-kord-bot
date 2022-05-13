package io.github.rawr.bot.music

import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager
import dev.kord.common.entity.Snowflake

object MusicQueue
{
    val queue = mutableListOf<VideoEntry>()
    val queueEmbedData = hashMapOf<Snowflake, List<VideoEntry>>()

    val playerManager = DefaultAudioPlayerManager()

    fun add(entry: VideoEntry)
    {
        entry.download()

        this.queue.add(entry)
    }
}

