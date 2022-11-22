package cat.kiwi.minecraft.metcd.uhcreloaded.patch

import cat.kiwi.minecraft.metcd.MEtcd
import cat.kiwi.minecraft.metcd.model.GameStatus
import me.xericker.uhcreloaded.UHCReloaded
import me.xericker.uhcreloaded.core.game.Game
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.popcraft.chunky.api.ChunkyAPI


@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
class UHCPatch : JavaPlugin() {
    companion object {
        lateinit var instance: UHCPatch
        var uhcPG = false
        var uhcDM = false
    }

    override fun onEnable() {
        logger.info("UHCPatch enabled")
        instance = this

        Config.readConfig()
        object : BukkitRunnable() {
            override fun run() {
                val chunky = Bukkit.getServer().servicesManager.load(ChunkyAPI::class.java)!!
                chunky.startTask(
                    "uhc-game",
                    "square",
                    0.0,
                    0.0,
                    Config.uhcWorldRadius,
                    Config.uhcWorldRadius,
                    "concentric"
                )
                chunky.startTask(
                    "uhc-deathmatch",
                    "square",
                    0.0,
                    0.0,
                    Config.uhcDeathMatchRadius,
                    Config.uhcDeathMatchRadius,
                    "concentric"
                )
                chunky.onGenerationComplete { event ->
                    logger.info("Generation completed for " + event.world())
                    if (event.world == "uhc-game") {
                        uhcPG = true
                    }
                    if (event.world == "uhc-deathmatch") {
                        uhcDM = true
                    }
                }
            }
        }.runTaskLaterAsynchronously(this, 200)
        object : BukkitRunnable() {
            override fun run() {
                if (!(uhcDM && uhcPG)) {
                    MEtcd.setGameStatus(GameStatus.LOADING)
                    return
                }
                val gameState = UHCReloaded.getGame().gameState
                logger.info("Reported $gameState")
                when (gameState) {
                    Game.GameState.WAITING -> MEtcd.setGameStatus(GameStatus.WAITING)
                    Game.GameState.STARTING -> MEtcd.setGameStatus(GameStatus.STARTING)
                    Game.GameState.IN_GAME -> MEtcd.setGameStatus(GameStatus.RUNNING)
                    Game.GameState.RESTARTING -> MEtcd.setGameStatus(GameStatus.ENDING)
                }
            }
        }.runTaskTimerAsynchronously(this, 20, 100)
    }

    override fun onDisable() {
        logger.info("UHCPatch disabled")
    }

}