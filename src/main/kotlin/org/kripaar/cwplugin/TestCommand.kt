package org.kripaar.cwplugin

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TestCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender?, cmd: Command?, str: String?, args: Array<out String>?): Boolean {
        sender as Player
        sender.sendMessage(sender.world.uid.toString())
        return true
    }

}
