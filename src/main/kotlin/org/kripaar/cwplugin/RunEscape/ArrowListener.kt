package org.kripaar.cwplugin.RunEscape

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.EnderChest
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import java.lang.Thread.sleep
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import java.time.LocalDate
import java.time.LocalDateTime


class ArrowListener : Listener {
    val list: MutableMap<Player, MutableList<PlayerAndEnderChest>> = mutableMapOf()

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
    fun onPlayerUse(event: PlayerInteractEvent) {
        var player: Player = event.player
        val block: Block = event.clickedBlock
        val location: Location = block.location

        var connection = DriverManager.getConnection("jdbc:sqlite:data/run_escape.sqlite")

        var statement: Statement = connection.createStatement()

        var r: ResultSet = statement.executeQuery("SELECT * FROM ender_chest WHERE x=${location.x} AND y=${location.y} AND z=${location.z}")

        if (!r.next()) { // No Result found. Passed
            return
        }

        player.openInventory(player.enderChest)
        player.closeInventory()

        if (list[player] == null) list[player] = mutableListOf()

        var final_: PlayerAndEnderChest? = null

        for (p in list[player]!!) {
            if (p.block == block) {
                final_ = p
            }
        }


        if (final_ == null) { // Check if player had been clicked this block
            // This code will run because
            final_ = PlayerAndEnderChest(player, block)
            list[player]?.add(final_)
        }

        if (final_.times >= 3) {
            player.sendMessage("已經到達上限")
            return
        }

        if (!final_.addItems()) {
            player.sendMessage("你還需要等待才可以獲得")
            event.isCancelled = true
            return
        }
        player.inventory.addItem(ItemStack(Material.ARROW, 3))
        if (player.health + 2 >= 20) player.health = 20.0
        else player.health += 2

        player.sendMessage("你獲得3支箭矢和2點血量")
        event.isCancelled = true
    }

    class PlayerAndEnderChest(var player: Player, var block: Block) {
        var times = 0
        var lastGet: LocalDateTime? = null

        fun addItems (): Boolean {
            if (!checkIfExpired()) return false
            print(checkIfExpired())
            ++times
            lastGet = LocalDateTime.now()
            return true
        }

        private fun checkIfExpired(): Boolean {
            if (lastGet == null) return true
            return lastGet?.plusSeconds(3)!!.isBefore(LocalDateTime.now())
        }
    }
}
