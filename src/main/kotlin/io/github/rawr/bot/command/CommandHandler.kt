package io.github.rawr.bot.command

import io.github.rawr.bot.command.impl.*
import io.github.rawr.bot.command.impl.percentage.type.GayCommand
import io.github.rawr.bot.command.impl.percentage.type.LoveCommand
import io.github.rawr.bot.command.impl.percentage.type.StupidCommand
import io.github.rawr.bot.command.impl.percentage.type.TrannyCommand
import io.github.rawr.bot.music.commands.QueueCommand

object CommandHandler
{
    val commands = listOf(
        DickCommand,
        NameCommand,
        GayCommand,
        TrannyCommand,
        MentionsCommand,
        BalanceCommand,
        DebugCommand,
        AutoRoleCommand,
        RedLikeRosesCommand,
        WorkCommand,
        SpamCommand,
        AssignRolesCommand,
        LoveCommand,
        StupidCommand,
        QueueCommand
    )

    val cooldowns = hashMapOf<Long, Long>()
    var prefix = '!'

    const val cooldown = 2000
}