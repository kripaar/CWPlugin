package org.kripaar.cwplugin.BedWars

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class GameController : CommandExecutor {
    private val plugin = Bukkit.getServer().pluginManager.getPlugin("CWPlugin")
    override fun onCommand(sender: CommandSender?, cmd: Command?, string: String?, args: Array<out String>?): Boolean {
//        print("Hi!")
        var gameSession = GameSession()

        gameSession.runTaskTimer(plugin, 0L, 60L)
        return true
    }
}