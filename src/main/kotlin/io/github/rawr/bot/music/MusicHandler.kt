package io.github.rawr.bot.music

import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.SearchResult

object MusicHandler
{
    private val youtube by lazy {
        YouTube.Builder(
            NetHttpTransport(), JacksonFactory()
        ) {}
            .setApplicationName("tits-music")
            .build()
    }

    fun retrieve(query: String): List<SearchResult>
    {
        val search = youtube.search().list(
            listOf(
                "id",
                "snippet"
            )
        ).also {
            it.key = "AIzaSyDNuXJj8cShTECHKyXeGDvdt9oZK51t2kU"
            it.q = query
            it.type = listOf("video")
            it.fields = "items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)"
            it.maxResults = 5
        }

        return search.execute().items
    }

    fun addToQueue(result: SearchResult)
    {
        MusicQueue.add(
            VideoEntry(
                title = result.snippet.title,
                thumbnail = result.snippet.thumbnails.default.url,
                url = result.id.videoId,
            )
        )
    }
}