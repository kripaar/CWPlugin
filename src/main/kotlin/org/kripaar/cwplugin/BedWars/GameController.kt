package org.kripaar.cwplugin.BedWars

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class GameController : CommandExecutor {
    private val plugin = Bukkit.getServer().pluginManager.getPlugin("CWPlugin")
    var gameSession = GameSession()

    override fun onCommand(sender: CommandSender, cmd: Command, string: String, args: Array<out String>?): Boolean {
        if (cmd.name == "kripaars-bw-start") {
            gameSession.runTaskTimer(plugin, 0L, 60L)
        } else if (cmd.name == "kripaars-bw-stop") {
            if (gameSession.isCancelled) {
                return false
            }
            gameSession.cancel()
            gameSession = GameSession()
        }
        return true
    }
}