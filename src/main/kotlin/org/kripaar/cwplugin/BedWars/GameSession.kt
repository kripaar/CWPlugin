package org.kripaar.cwplugin.BedWars

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class GameSession : BukkitRunnable() {
    private val world: World = Bukkit.getServer().getWorld(UUID.fromString("48d090da-8c76-45e6-8580-c165eda38780"))
    private var times = 0

    private val orangeResSpawn: Location = Location(world, -63.0, 23.0, 13.0)
    private val blueResSpawn: Location = Location(world, 80.0, 24.0, 3.0)
    private val emeraldSpawns: List<Location> = listOf(Location(world, 21.0, 23.0, 8.0), Location(world, -4.0, 23.0, 8.0))


    override fun run() {
        if (times >= 10) {
            for (e in emeraldSpawns) {
                world.dropItemNaturally(e, ItemStack(Material.EMERALD, 1))
            }
            times = 0
        }

        world.dropItemNaturally(orangeResSpawn, ItemStack(Material.IRON_INGOT, 5))
        world.dropItemNaturally(blueResSpawn, ItemStack(Material.IRON_INGOT, 5))
        ++times
    }
}