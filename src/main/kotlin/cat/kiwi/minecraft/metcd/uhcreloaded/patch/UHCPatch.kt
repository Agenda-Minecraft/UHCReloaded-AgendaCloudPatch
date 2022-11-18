package cat.kiwi.minecraft.metcd.uhcreloaded.patch

import cat.kiwi.minecraft.metcd.MEtcd
import cat.kiwi.minecraft.metcd.model.GameStatus
import com.gmail.xericker.uhcreloaded.UHCReloaded
import com.gmail.xericker.uhcreloaded.core.game.Game
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class UHCPatch : JavaPlugin() {
    override fun onEnable() {
        logger.info("UHCPatch enabled")

        object : BukkitRunnable() {
            override fun run() {
                when (UHCReloaded.getGame().gameState) {
                    Game.GameState.WAITING -> MEtcd.setGameStatus(GameStatus.WAITING)
                    Game.GameState.STARTING -> MEtcd.setGameStatus(GameStatus.STARTING)
                    Game.GameState.IN_GAME -> MEtcd.setGameStatus(GameStatus.RUNNING)
                    Game.GameState.RESTARTING -> MEtcd.setGameStatus(GameStatus.ENDING)
                }
            }
        }.runTaskTimerAsynchronously(this, 20, 1000)
    }

    override fun onDisable() {
        logger.info("UHCPatch disabled")
    }

}