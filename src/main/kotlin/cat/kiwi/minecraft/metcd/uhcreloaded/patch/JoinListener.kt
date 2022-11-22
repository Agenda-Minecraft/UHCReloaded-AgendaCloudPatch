package cat.kiwi.minecraft.metcd.uhcreloaded.patch

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLoginEvent

class JoinListener : Listener {
    @EventHandler
    fun onPlayerJoinEvent(e: PlayerJoinEvent) {
        if (e.player.hasPermission("uhcpatch.bypass")) return
        val instance = UHCPatch.instance
        if (!(UHCPatch.uhcDM && UHCPatch.uhcPG)) {
            e.player.kickPlayer(Config.notCompleteMsg)
            instance.logger.info("kicked player ${e.player.name}")
        }
    }
}