package io.github.rawr.bot.util

import dev.kord.core.entity.ReactionEmoji

object NumberEmoji
{
    private val emojiMap = mapOf(
        1 to ":one:",
        2 to ":two:",
        3 to ":three:",
        4 to ":four:",
        5 to ":five:",
        6 to ":six:",
        7 to ":seven:",
        8 to ":eight:",
        9 to ":nine:"
    )

    private val emojiUnicodeMap = mapOf(
        1 to "1️⃣",
        2 to "2️⃣",
        3 to "3️⃣",
        4 to "4️⃣",
        5 to "5️⃣",
        6 to "6️⃣",
        7 to "7️⃣",
        8 to "8️⃣",
        9 to "9️⃣"
    )

    fun getEmoji(number: Int): String
    {
        return emojiMap[number]!!
    }

    fun getEmojiUnicode(number: Int): String
    {
        return emojiUnicodeMap[number]!!
    }

    fun getReactionEmoji(number: Int): ReactionEmoji
    {
        return ReactionEmoji.Unicode(this.getEmojiUnicode(number))
    }

    fun emojiToNumber(emoji: ReactionEmoji): Int
    {
        val reversed = this.emojiUnicodeMap.entries.associateBy({ it.value }) {
            it.key
        }

        return reversed[emoji.name]!!
    }
}