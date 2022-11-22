package cat.kiwi.minecraft.metcd.uhcreloaded.patch

import cat.kiwi.minecraft.metcd.MEtcd
import cat.kiwi.minecraft.metcd.model.GameStatus
import me.xericker.uhcreloaded.UHCReloaded
import me.xericker.uhcreloaded.core.game.Game
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
class UHCPatch : JavaPlugin() {
    override fun onEnable() {
        logger.info("UHCPatch enabled")

        object : BukkitRunnable() {
            override fun run() {
                val gameState = UHCReloaded.getGame().gameState

                logger.info("Reported $gameState")
                when (gameState) {
                    Game.GameState.WAITING -> MEtcd.setGameStatus(GameStatus.WAITING)
                    Game.GameState.STARTING -> MEtcd.setGameStatus(GameStatus.WAITING)
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