package io.github.rawr.bot.music

import com.google.api.services.youtube.model.SearchResult
import kong.unirest.Unirest
import java.io.File

class VideoEntry(
    val title: String,
    val thumbnail: String,
    val url: String,
)
{
    constructor(result: SearchResult) : this(result.snippet.title, result.snippet.thumbnails.default.url, result.id.videoId)

    var file: File? = null

    fun download()
    {
        val url = "https://ytmp4.buzz/api/json/mp3/${url}"
        val json = Unirest.get(url).asJson().body.`object`

        val array = json.getJSONArray("vidInfo")
        val download = array
            .getJSONObject(0)
            .getString("dloadUrl")
            .replace("\\ ", "")
            .replace("//", "/")
            .replace("https:/", "https://")

        println(download)

        this.file = Unirest.get(download)
            .asFile("videos/$title.mp3")
            .body
    }
}