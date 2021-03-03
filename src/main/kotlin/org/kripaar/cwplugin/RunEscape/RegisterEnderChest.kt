package org.kripaar.cwplugin.RunEscape

import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement


class RegisterEnderChest : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, string: String, args: Array<out String>): Boolean {
        sender as Player


        val location: Location = sender.getTargetBlock(null, 100).location

        var connection = DriverManager.getConnection("jdbc:sqlite:data/run_escape.sqlite")

        var statement: Statement = connection.createStatement()

        statement.executeUpdate("CREATE TABLE IF NOT EXISTS ender_chest (x INT, y INT, z INT)")

        var r: ResultSet = statement.executeQuery("SELECT * FROM ender_chest WHERE x=${location.x} AND y=${location.y} AND z=${location.z}")

        if (!r.next()) { // Not true - Not Exists.
            statement.executeUpdate("INSERT INTO ender_chest (x, y, z) VALUES (${location.x}, ${location.y}, ${location.z})")
            sender.sendMessage("Saved the block at ${location.x}, ${location.y}, ${location.z}")
        } else {
            sender.sendMessage("This block has been saved. Please try again or remove the record first.")
        }

        return true
    }
}