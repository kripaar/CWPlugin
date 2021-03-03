package org.kripaar.cwplugin

import org.bukkit.plugin.java.JavaPlugin
import org.kripaar.cwplugin.BedWars.GameController
import org.kripaar.cwplugin.RunEscape.ArrowListener
import org.kripaar.cwplugin.RunEscape.RegisterEnderChest
import org.kripaar.cwplugin.RunEscape.RemoveEnderChest
import java.sql.DriverManager
import java.sql.Statement


class CWPlugin : JavaPlugin() {
    override fun onEnable() {
        registerCommands()
        this.server.pluginManager.registerEvents(ArrowListener(), this)

        var connection = DriverManager.getConnection("jdbc:sqlite:data/run_escape.sqlite")

        var statement: Statement = connection.createStatement()
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS ender_chest (x INT, y INT, z INT)")

        super.onEnable()
    }

    override fun onDisable() {
        super.onDisable()
    }

    private fun registerCommands() {
        this.getCommand("kripaars-re-register").executor = RegisterEnderChest()
        this.getCommand("kripaars-re-remove").executor = RemoveEnderChest()
        this.getCommand("kripaars-bw-start").executor = GameController()
        this.getCommand("kripaars-ts-test").executor = TestCommand()
//        this.getCommand("CW:BW:end").executor = GameController()
    }
}