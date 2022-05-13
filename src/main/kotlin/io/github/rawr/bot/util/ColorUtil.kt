package io.github.rawr.bot.util

import dev.kord.common.Color
import kotlin.random.Random

object ColorUtil
{
    fun randomColor(): Color
    {
        val range = 0..3
        val colors = range
            .map {
                Random(System.nanoTime()).nextInt(255)
            }

        return Color(colors[0], colors[1], colors[2])
    }
}